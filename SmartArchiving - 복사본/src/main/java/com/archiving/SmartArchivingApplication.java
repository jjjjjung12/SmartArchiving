package com.archiving;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.archiving")
@MapperScan(
        basePackages = "com.archiving.**.dao.mapper",
        sqlSessionFactoryRef = "sqlSessionFactory")
public class SmartArchivingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartArchivingApplication.class, args);
    }
}
