package com.archiving.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(
        basePackages = "com.archiving.**.dao.sqream",
        sqlSessionFactoryRef = "sqreamSqlSessionFactory")
public class SqreamMapperScanConfig {
}
