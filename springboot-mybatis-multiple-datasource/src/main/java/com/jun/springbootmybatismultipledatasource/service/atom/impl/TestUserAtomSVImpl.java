package com.jun.springbootmybatismultipledatasource.service.atom.impl;

import com.jun.springbootmybatismultipledatasource.dao.mapper.db1.bo.TestUser;
import com.jun.springbootmybatismultipledatasource.dao.mapper.db1.bo.TestUserCriteria;
import com.jun.springbootmybatismultipledatasource.dao.mapper.factory.MapperFactory;
import com.jun.springbootmybatismultipledatasource.service.atom.interfaces.TestUserAtomSV;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class TestUserAtomSVImpl implements TestUserAtomSV {
    @Override
    public int addTestUser(TestUser req) {
        return MapperFactory.getTestUserMapper().insertSelective(req);
    }

    @Override
    public List<TestUser> queryTestUser(TestUser req) {
        TestUserCriteria testUserCriteria = new TestUserCriteria();
        TestUserCriteria.Criteria criteria = testUserCriteria.createCriteria();
        criteria.andUserIdEqualTo(req.getUserId());
        List<TestUser> list = MapperFactory.getTestUserMapper().selectByExample(testUserCriteria);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list;
    }
}
