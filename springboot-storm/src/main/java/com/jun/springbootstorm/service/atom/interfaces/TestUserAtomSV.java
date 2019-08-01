package com.jun.springbootstorm.service.atom.interfaces;

import com.jun.springbootstorm.dao.mapper.bo.TestUser;
import org.springframework.stereotype.Service;

import java.util.List;
public interface TestUserAtomSV {

    List<TestUser> queryTestUser(TestUser req);

    int addTestUser(TestUser req);

    int updateTestUser(TestUser req);

    int delTestUser(TestUser req);
}
