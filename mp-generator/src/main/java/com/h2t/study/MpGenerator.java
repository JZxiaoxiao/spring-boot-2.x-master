package com.h2t.study;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器
 *
 */
public class MpGenerator {
    /**
     * 包名
     */
    private static final String PACKAGE_NAME = "com.jun.springbootmybatisdynamicdatasource";

	/**
     * 代码生成路径
     */
	private static String OUTPUT_DIR = System.getProperty("user.dir").concat("\\generator\\");
    /**
     * 代码注释作者
     */
    private static final String AUTHOR = "JZxiaoxiao";

    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String HOST = "127.0.0.1";
    private static final String PORT = "3306";
    /**
     * 数据库信息
     */
    private static final String DATABASE = "iov";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/iov?characterEncoding=UTF8&serverTimezone = UTC";

    public static void main(String[] args) {
        System.out.println(OUTPUT_DIR);
        // 代码生成器
        final AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(buildGlobalConfig());
        // 数据源配置
        mpg.setDataSource(buildDataSourceConfig());
        // 包配置
        mpg.setPackageInfo(buildPackageConfig());
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templateXmlPath = "/templates/mapper.xml.ftl";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templateXmlPath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return OUTPUT_DIR + "/resources/mapper/"
                    + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        //不生成mapper xml文件,由上面控制生成位置
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);
        // 策略配置
        mpg.setStrategy(buildStrategyConfig());
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    /**
     * 全局构造配置类
     *
     * @return
     * */
    private static GlobalConfig buildGlobalConfig() {
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //项目所在地址
        gc.setOutputDir(OUTPUT_DIR);
        //注释作者
        gc.setAuthor(AUTHOR);
        //生成文件不打开
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        //生成result map
        // XML ResultMap
        gc.setBaseResultMap(true);
        //生成java mysql字段映射
        // XML columList
        gc.setBaseColumnList(true);
        gc.setSwagger2(true); // 实体属性 Swagger2 注解
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        return gc;
    }

    /**
     * 数据库配置信息
     *
     * @return
     * */
    private static DataSourceConfig buildDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(URL);
        dsc.setDriverName(DRIVER_NAME);
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASSWORD);
        dsc.setTypeConvert(new OracleTypeConvert() {
            @Override
            public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                //将数据库中timestamp转换成date
                if ( fieldType.toLowerCase().contains( "timestamp" ) || fieldType.toLowerCase().contains( "date" )) {
                    return DbColumnType.LOCAL_DATE_TIME;
                }
                if(fieldType.toLowerCase().contains("clob")){
                    return DbColumnType.STRING;
                }

                return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
            }
        });
        return dsc;
    }

    private static PackageConfig buildPackageConfig() {
        PackageConfig pc = new PackageConfig();
        //pc.setModuleName(scanner("模块名"));
        pc.setParent(PACKAGE_NAME);
        pc.setEntity("entity");
        pc.setXml("mapper");
        pc.setController("controller");
        pc.setService("service");
        return pc;
    }

    private static StrategyConfig buildStrategyConfig() {
        StrategyConfig strategy = new StrategyConfig();
        // 命名规则
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        // 默认生成的po类不继承，手动修改继承
        //strategy.setSuperEntityClass((String) null);
        // 实体是否使用Lombok插件
        strategy.setEntityLombokModel(true);
        // 自定义 service 父类
        //strategy.setSuperServiceClass("com.h2t.study.BaseService");
        // 自定义 service 实现类父类
        //strategy.setSuperServiceImplClass("com.h2t.study.BaseServiceImpl");
        // 控制层是否使用Rest风格
        strategy.setRestControllerStyle(true);
        //去除无效表
        strategy.setExclude(new String[]{"BIN\\$\\S*"});
        //只生成指定表（和上面同时只能选一个）
        //strategy.setInclude("CMP_PBOSS_PRODUCT");
        return strategy;
    }
}
