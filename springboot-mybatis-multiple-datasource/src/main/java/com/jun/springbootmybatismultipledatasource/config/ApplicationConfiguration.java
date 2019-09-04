package com.jun.springbootmybatismultipledatasource.config;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 
 * @Title: ApplicationConfiguration
 * @Description:
 * 获取application文件的信息
 * @Version:1.0.0
 * @author JZxiaoxiao
 * @date 2019年9月2日
 */
@Component("applicationConfiguration")
public class ApplicationConfiguration {

	public static Properties build(Environment env, String prefix) {
		Properties prop = new Properties();
		prop.put("url", env.getProperty(prefix + "url"));
		prop.put("username", env.getProperty(prefix + "username"));
		prop.put("password", env.getProperty(prefix + "password"));
		prop.put("minPoolSize", env.getProperty(prefix + "minPoolSize"));
		prop.put("maxPoolSize", env.getProperty(prefix + "maxPoolSize"));
		prop.put("maxLifetime", env.getProperty(prefix + "maxLifetime"));
		prop.put("borrowConnectionTimeout", env.getProperty(prefix + "borrowConnectionTimeout"));
		prop.put("loginTimeout", env.getProperty(prefix + "loginTimeout"));
		prop.put("maintenanceInterval", env.getProperty(prefix + "maintenanceInterval"));
		prop.put("maxIdleTime", env.getProperty(prefix + "maxIdleTime"));
		prop.put("testQuery", env.getProperty(prefix + "testQuery"));
		return prop;
	}
	
}