package com.jun.springbootmybatismultipledatasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * @Description:
 * springboot
 * 多数据源
 * 多数据源单个事务
 * 多数据源分布式事务
 * @author JZxiaoxiao
 * @date 2019年9月1日
 */
@SpringBootApplication
@EnableTransactionManagement
public class SpringbootMybatisMultipleDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisMultipleDatasourceApplication.class, args);
    }

}
