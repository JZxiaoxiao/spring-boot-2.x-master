package com.jun.springbootstorm.service.atom.impl;

import com.jun.springbootstorm.dao.mapper.bo.TestUser;
import com.jun.springbootstorm.dao.mapper.bo.TestUserCriteria;
import com.jun.springbootstorm.dao.mapper.factory.MapperFactory;
import com.jun.springbootstorm.service.atom.interfaces.TestUserAtomSV;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestUserAtomSVImpl implements TestUserAtomSV {
    @Override
    public List<TestUser> queryTestUser(TestUser req) {
        TestUserCriteria testUserCriteria = new TestUserCriteria();
        TestUserCriteria.Criteria criteria = testUserCriteria.createCriteria();
        criteria.andUserIdEqualTo(req.getUserId());
        criteria.andUserNameEqualTo(req.getUserName());
        criteria.andUserAgeEqualTo(req.getUserAge());
        return MapperFactory.getTestUserMapper().selectByExample(testUserCriteria);
    }

    @Override
    public int addTestUser(TestUser req) {
        return MapperFactory.getTestUserMapper().insert(req);
    }

    @Override
    public int updateTestUser(TestUser req) {
        return MapperFactory.getTestUserMapper().updateByPrimaryKeySelective(req);
    }

    @Override
    public int delTestUser(TestUser req) {
        return MapperFactory.getTestUserMapper().deleteByPrimaryKey(req.getUserId());
    }
}
