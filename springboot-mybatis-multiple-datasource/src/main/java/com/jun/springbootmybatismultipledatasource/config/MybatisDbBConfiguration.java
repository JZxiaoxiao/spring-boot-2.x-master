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
@MapperScan(basePackages = {"com.jun.springbootmybatismultipledatasource.dao.mapper.db2"}, sqlSessionFactoryRef = "sqlSessionFactory2")
public class MybatisDbBConfiguration {

    @Autowired
    @Qualifier("db2")
    private DataSource db2;

    @Bean(name = "sqlSessionFactory2")
    public SqlSessionFactory sqlSessionFactory2() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(db2);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/db2/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "sqlSessionTemplate2")
    public SqlSessionTemplate sqlSessionTemplate2(
            @Qualifier("sqlSessionFactory2") SqlSessionFactory sqlSessionFactory2) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory2);
    }
}
