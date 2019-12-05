package com.jun.springbootmybatisdynamicdatasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.jun.springbootmybatisdynamicdatasource.dao.mapper")
public class SpringbootMybatisDynamicDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisDynamicDatasourceApplication.class, args);
    }

}
