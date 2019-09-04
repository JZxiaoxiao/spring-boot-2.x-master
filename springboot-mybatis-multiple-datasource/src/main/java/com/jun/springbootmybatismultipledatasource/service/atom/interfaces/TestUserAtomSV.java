package com.jun.springbootmybatismultipledatasource.service.atom.interfaces;

import com.jun.springbootmybatismultipledatasource.dao.mapper.db1.bo.TestUser;

import java.util.List;

public interface TestUserAtomSV {

    int addTestUser(TestUser req);

    List<TestUser> queryTestUser(TestUser req);
}
