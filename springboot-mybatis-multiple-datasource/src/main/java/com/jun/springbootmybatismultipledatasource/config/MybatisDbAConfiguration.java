package com.jun.springbootmybatismultipledatasource.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.sql.DataSource;

/**
 * @Description:
 * 启用分布式事务配置多数据源
 * @author JZxiaoxiao
 * @date 2019年9月1日
 */
@Configuration
@MapperScan(basePackages = {"com.jun.springbootmybatismultipledatasource.dao.mapper.db1"}, sqlSessionFactoryRef = "sqlSessionFactory1")
public class MybatisDbAConfiguration {

    @Autowired
    @Qualifier("db1")
    private DataSource db1;

    @Bean(name = "sqlSessionFactory1")
    public SqlSessionFactory sqlSessionFactory1() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(db1);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/db1/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "sqlSessionTemplate1")
    public SqlSessionTemplate sqlSessionTemplate1(
            @Qualifier("sqlSessionFactory1") SqlSessionFactory sqlSessionFactory1) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory1);
    }

}
