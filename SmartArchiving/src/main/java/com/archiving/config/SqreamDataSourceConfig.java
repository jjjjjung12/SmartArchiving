package com.archiving.config;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import com.archiving.util.CryptoUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class SqreamDataSourceConfig {

	/* Sqream DataSource(HikariCP) 생성(환경에 따라 Sqream 드라이버/PG 드라이버 선택) */
    @Bean(name = "sqreamDataSource")
    public DataSource sqreamDataSource(Environment env) {
        String host = env.getProperty("app.db.sqream.host", "localhost");
        String port = env.getProperty("app.db.sqream.port", "3108");
        String dbName = env.getProperty("app.db.sqream.db-name", "master");
        String username = env.getProperty("app.db.sqream.username", "");
        String password = decryptIfNeeded(env.getProperty("app.db.sqream.password", ""));

        HikariConfig cfg = new HikariConfig();
        
        //postgresql 로컬용 우회 접속
        boolean pgDriver = env.getProperty("app.db.sqream.use-postgresql-driver", Boolean.class, Boolean.FALSE);

        if (pgDriver) {
            cfg.setJdbcUrl("jdbc:postgresql://" + host + ":" + port + "/" + dbName);
            cfg.setDriverClassName("org.postgresql.Driver");
            cfg.setUsername(username);
            cfg.setPassword(password);
        } else {
            String jdbcUrl = "jdbc:Sqream://" + host + ":" + port + "/" + dbName
                    + ";user=" + username + ";password=" + password + ";cluster=true;fetchSize=1";
            cfg.setJdbcUrl(jdbcUrl);
            cfg.setDriverClassName("com.sqream.jdbc.SQDriver");
        }

        cfg.setMaximumPoolSize(intProp(env, "app.db.sqream.max-pool-size", 3));
        cfg.setMinimumIdle(intProp(env, "app.db.sqream.min-idle", 0));
        cfg.setConnectionTimeout(longProp(env, "app.db.sqream.connection-timeout", 5000L));
        if (!pgDriver) {
            cfg.setInitializationFailTimeout(-1);
        }
        cfg.setPoolName("sqream-pool");
        return new HikariDataSource(cfg);
    }

    /* Sqream JdbcTemplate 생성 */
    @Bean(name = "sqreamJdbcTemplate")
    public JdbcTemplate sqreamJdbcTemplate(@Qualifier("sqreamDataSource") DataSource sqreamDataSource) {
        return new JdbcTemplate(sqreamDataSource);
    }

    /* Sqream용 MyBatis SqlSessionFactory 생성(매퍼 로딩/로컬 로그 설정) */
    @Bean(name = "sqreamSqlSessionFactory")
    public SqlSessionFactory sqreamSqlSessionFactory(
            @Qualifier("sqreamDataSource") DataSource sqreamDataSource,
            Environment env) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(sqreamDataSource);
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mappers/sqream/**/*.xml"));

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        if (isLocalProfile(env)) {
            configuration.setLogImpl(StdOutImpl.class);
        }
        factoryBean.setConfiguration(configuration);
        return factoryBean.getObject();
    }

    /* 현재 활성 프로파일에 local 포함 여부 확인 */
    private static boolean isLocalProfile(Environment env) {
        for (String p : env.getActiveProfiles()) {
            if ("local".equalsIgnoreCase(p)) {
                return true;
            }
        }
        return false;
    }

    /* 암호화된 패스워드(레거시 base64 관례) 복호화 시도, 실패 시 원문 사용 */
    private static String decryptIfNeeded(String value) {
        if (StringUtils.isBlank(value)) {
            return "";
        }
        try {
            return new CryptoUtil().decrypt(value.trim());
        } catch (Exception ignored) {
            return value;
        }
    }

    /* 환경변수 int 프로퍼티 조회(기본값 제공) */
    private static int intProp(Environment env, String key, int def) {
        try {
            return Integer.parseInt(env.getProperty(key, String.valueOf(def)));
        } catch (Exception e) {
            return def;
        }
    }

    /* 환경변수 long 프로퍼티 조회(기본값 제공) */
    private static long longProp(Environment env, String key, long def) {
        try {
            return Long.parseLong(env.getProperty(key, String.valueOf(def)));
        } catch (Exception e) {
            return def;
        }
    }
}
