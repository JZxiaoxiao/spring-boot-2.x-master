package com.jun.springbootmybatismultipledatasource.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Description:
 * 配置多数据源
 * @Version:1.0.0
 * @author JZxiaoxiao
 * @date 2019年9月2日
 */
@Configuration
public class DataSourceConfiguration {

    /**
     * @Description:
     * 不启用分布式事务配置多数据源
     * @author JZxiaoxiao
     * @date 2019年9月1日
     */
//    @Bean(name = "db1")
//    @ConfigurationProperties(prefix = "spring.datasource.db1")
//    public DataSource dataSource1() {
//        return DataSourceBuilder.create().build();
//    }
//    @Bean(name = "db2")
//    @ConfigurationProperties(prefix = "spring.datasource.db2")
//    public DataSource dataSource2() {
//        return DataSourceBuilder.create().build();
//    }

    /**
     * @Description:
     * 启用分布式事务配置多数据源
     * @author JZxiaoxiao
     * @date 2019年9月1日
     */

    @Bean(name = "db1")
    public DataSource db1(Environment env) throws SQLException {
        Properties prop = ApplicationConfiguration.build(env,"mysql.datasource.dbA.");
        return initDataSource(prop,"db1");
    }

    @Bean(name = "db2")
    public DataSource dataSource2(Environment env) throws SQLException {
        Properties prop = ApplicationConfiguration.build(env,"mysql.datasource.dbB.");
        return initDataSource(prop,"db2");
    }

    private AtomikosDataSourceBean initDataSource(Properties prop,String dbName) throws SQLException{
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(prop.get("url").toString());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(prop.get("password").toString());
        mysqlXaDataSource.setUser(prop.get("username").toString());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);

        // 将本地事务注册到创 Atomikos全局事务
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName(dbName);

        xaDataSource.setMinPoolSize(Integer.parseInt(prop.get("minPoolSize").toString()));
        xaDataSource.setMaxPoolSize(Integer.parseInt(prop.get("maxPoolSize").toString()));
        xaDataSource.setMaxLifetime(Integer.parseInt(prop.get("maxLifetime").toString()));
        xaDataSource.setBorrowConnectionTimeout(Integer.parseInt(prop.get("borrowConnectionTimeout").toString()));
        xaDataSource.setLoginTimeout(Integer.parseInt(prop.get("loginTimeout").toString()));
        xaDataSource.setMaintenanceInterval(Integer.parseInt(prop.get("maintenanceInterval").toString()));
        xaDataSource.setMaxIdleTime(Integer.parseInt(prop.get("maxIdleTime").toString()));
        xaDataSource.setTestQuery(prop.get("testQuery").toString());
        return xaDataSource;
    }
}
