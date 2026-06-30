package com.archiving.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.archiving.util.CryptoUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class AppPostgresDataSourceConfig {

    @Bean
    @Primary
    /* PostgreSQL DataSource(HikariCP) 생성 */
    public DataSource dataSource(Environment env) {
        String host = env.getProperty("app.db.postgresql.host", "localhost");
        String port = env.getProperty("app.db.postgresql.port", "5432");
        String dbName = env.getProperty("app.db.postgresql.db-name", "postgres");
        String username = env.getProperty("app.db.postgresql.username", "postgres");
        String password = decryptIfNeeded(env.getProperty("app.db.postgresql.password", ""));

        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl("jdbc:postgresql://" + host + ":" + port + "/" + dbName);
        cfg.setUsername(username);
        cfg.setPassword(password);
        cfg.setDriverClassName("org.postgresql.Driver");
        cfg.setMaximumPoolSize(intProp(env, "app.db.postgresql.max-pool-size", 5));
        cfg.setMinimumIdle(intProp(env, "app.db.postgresql.min-idle", 1));
        cfg.setConnectionTimeout(longProp(env, "app.db.postgresql.connection-timeout", 5000L));

        return new HikariDataSource(cfg);
    }

    /* mhdb(앱 메타) 전용 PostgreSQL SqlSessionFactory — mappers/sqream 은 제외(Sqream 전용 팩토리에서만 로드) */
    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, Environment env) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(primaryPostgresMapperLocations());

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        if (isLocalProfile(env)) {
            configuration.setLogImpl(StdOutImpl.class);
        }
        factoryBean.setConfiguration(configuration);
        return factoryBean.getObject();
    }

    /* classpath mappers//.xml 중 Sqream 전용 디렉터리(mappers/sqream) 제외 */
    private static Resource[] primaryPostgresMapperLocations() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] all = resolver.getResources("classpath:mappers/**/*.xml");
        List<Resource> out = new ArrayList<>(all.length);
        for (Resource r : all) {
            if (!isUnderSqreamMappers(r)) {
                out.add(r);
            }
        }
        return out.toArray(new Resource[0]);
    }

    private static boolean isUnderSqreamMappers(Resource r) throws IOException {
        String loc = r.getURL().toString().replace('\\', '/');
        return loc.contains("/mappers/sqream/") || loc.contains("/mappers%2Fsqream%2F");
    }

    /* 현재 활성 프로파일에 local 포함 여부 확인 */
    private boolean isLocalProfile(Environment env) {
        for (String p : env.getActiveProfiles()) {
            if ("local".equalsIgnoreCase(p)) {
                return true;
            }
        }
        return false;
    }

    /* 암호화된 패스워드(레거시 base64 관례) 복호화 시도, 실패 시 원문 사용 */
    private String decryptIfNeeded(String value) {
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
    private int intProp(Environment env, String key, int def) {
        try {
            return Integer.parseInt(env.getProperty(key, String.valueOf(def)));
        } catch (Exception e) {
            return def;
        }
    }

    /* 환경변수 long 프로퍼티 조회(기본값 제공) */
    private long longProp(Environment env, String key, long def) {
        try {
            return Long.parseLong(env.getProperty(key, String.valueOf(def)));
        } catch (Exception e) {
            return def;
        }
    }
}
