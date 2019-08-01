package com.jun.springbootstorm.dao.mapper.factory;

import com.jun.springbootstorm.dao.mapper.interfaces.TestUserMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MapperFactory {

    @Autowired
    private transient SqlSessionTemplate st;

    private static SqlSessionTemplate sqlSessionTemplate;

    @PostConstruct
    void init() {
        setSqlSessionTemplate(st);
    }

    public static void setSqlSessionTemplate(SqlSessionTemplate st) {
        MapperFactory.sqlSessionTemplate = st;
    }

    public static TestUserMapper getTestUserMapper() {
        return sqlSessionTemplate.getMapper(TestUserMapper.class);
    }
}
