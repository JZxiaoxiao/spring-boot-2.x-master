package com.jun.springbootmybatismultipledatasource.dao.mapper.factory;

import com.jun.springbootmybatismultipledatasource.dao.mapper.db1.interfaces.TestUserMapper;
import com.jun.springbootmybatismultipledatasource.dao.mapper.db2.interfaces.TestBookMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Description:
 * mybatis 工厂类
 * @author JZxiaoxiao
 * @date 2019年9月1日
 */
@Component
public class MapperFactory {

    @Autowired
    @Qualifier("sqlSessionTemplate1")
    private transient SqlSessionTemplate st1;

    @Autowired
    @Qualifier("sqlSessionTemplate2")
    private transient SqlSessionTemplate st2;

    private static SqlSessionTemplate sqlSessionTemplate1;

    private static SqlSessionTemplate sqlSessionTemplate2;

    @PostConstruct
    void init() {
        setSqlSessionTemplate(st1,st2);
    }

    public static void setSqlSessionTemplate(SqlSessionTemplate st1,SqlSessionTemplate st2) {
        MapperFactory.sqlSessionTemplate1 = st1;
        MapperFactory.sqlSessionTemplate2 = st2;
    }

    public static TestUserMapper getTestUserMapper() {
        return sqlSessionTemplate1.getMapper(TestUserMapper.class);
    }

    public static TestBookMapper getTestBookMapper() {
        return sqlSessionTemplate2.getMapper(TestBookMapper.class);
    }
}
