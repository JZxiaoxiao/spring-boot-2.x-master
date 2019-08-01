package com.jun.springbootstorm;

import com.jun.springbootstorm.storm.TopologyApp;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

//@MapperScan(basePackages = "com.jun.springbootstorm.dao")
@SpringBootApplication
public class SpringBootStormApplication {

    private static ConfigurableApplicationContext context = null;

    /**
     * storm 单机模式启动
     * @param args
     */
    public static void main(String[] args) {
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootStormApplication.class, args);
        //启动拓扑测试
        TopologyApp app = context.getBean(TopologyApp.class);
        app.test();
        //SpringApplication.exit(context);
    }

    public synchronized static void run(String ...args) {
        if(SpringBootStormApplication.context == null) {
            System.out.println("SpringBootStormApplication 开始启动");
            try {
                SpringApplication app = new SpringApplication(SpringBootStormApplication.class);
                app.setWebApplicationType(WebApplicationType.NONE);
                //忽略掉banner输出
                app.setBannerMode(Banner.Mode.OFF);
                //忽略Spring启动信息日志
                app.setLogStartupInfo(false);
                context = app.run(args);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("SpringBootStormApplication 启动失败：" + e.getMessage());
            }
        }
    }

}
