package com.archiving.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Allows legacy code (e.g. {@code new UtilClass()}) to access Spring beans when the
 * application context is available. When running a plain {@code main} without Spring,
 * {@link #getContext()} is null and callers should fall back to direct JDBC.
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

	private static volatile ApplicationContext context;

	@Nullable
	/* Spring ApplicationContext 반환(없으면 null) */
	public static ApplicationContext getContext() {
		return context;
	}

	/* 타입으로 Spring Bean 조회(컨텍스트 없으면 null) */
	public static <T> T getBean(Class<T> type) {
		ApplicationContext ctx = context;
		if (ctx == null) {
			return null;
		}
		return ctx.getBean(type);
	}

	/**
	 * Primary (PostgreSQL) JdbcTemplate used for legacy dynamic count queries.
	 */
	@Nullable
	/* 기본 JdbcTemplate 조회(빈 이름 또는 타입으로 시도) */
	public static JdbcTemplate getPrimaryJdbcTemplate() {
		ApplicationContext ctx = context;
		if (ctx == null) {
			return null;
		}
		try {
			return ctx.getBean("jdbcTemplate", JdbcTemplate.class);
		} catch (Exception e) {
			try {
				return ctx.getBean(JdbcTemplate.class);
			} catch (Exception e2) {
				return null;
			}
		}
	}

	/** Same pool as magicview Sqream JDBC stack ({@code sqreamJdbcTemplate}). */
	@Nullable
	/* Sqream JdbcTemplate 조회(없으면 null) */
	public static JdbcTemplate getSqreamJdbcTemplate() {
		ApplicationContext ctx = context;
		if (ctx == null) {
			return null;
		}
		try {
			return ctx.getBean("sqreamJdbcTemplate", JdbcTemplate.class);
		} catch (Exception e) {
			return null;
		}
	}

	@Nullable
	/* Sqream DataSource 조회(없으면 null) */
	public static DataSource getSqreamDataSource() {
		ApplicationContext ctx = context;
		if (ctx == null) {
			return null;
		}
		try {
			return ctx.getBean("sqreamDataSource", DataSource.class);
		} catch (Exception e) {
			return null;
		}
	}

	/** Primary app datasource (same as {@link #getPrimaryJdbcTemplate()}). */
	@Nullable
	/* 기본 DataSource 조회(기본 JdbcTemplate에서 역추출) */
	public static DataSource getPrimaryDataSource() {
		JdbcTemplate jt = getPrimaryJdbcTemplate();
		return jt != null ? jt.getDataSource() : null;
	}

	/** Primary PostgreSQL MyBatis factory (default Boot sqlSessionFactory). */
	@Nullable
	/* 기본 MyBatis SqlSessionFactory 조회 */
	public static SqlSessionFactory getPrimarySqlSessionFactory() {
		ApplicationContext ctx = context;
		if (ctx == null) {
			return null;
		}
		try {
			return ctx.getBean("sqlSessionFactory", SqlSessionFactory.class);
		} catch (Exception e) {
			try {
				return ctx.getBean(SqlSessionFactory.class);
			} catch (Exception e2) {
				return null;
			}
		}
	}

	/** Sqream MyBatis factory (magicview 전용). */
	@Nullable
	/* Sqream MyBatis SqlSessionFactory 조회 */
	public static SqlSessionFactory getSqreamSqlSessionFactory() {
		ApplicationContext ctx = context;
		if (ctx == null) {
			return null;
		}
		try {
			return ctx.getBean("sqreamSqlSessionFactory", SqlSessionFactory.class);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	/* Spring 컨텍스트 주입 시 정적 홀더에 저장 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextHolder.context = applicationContext;
	}
}
