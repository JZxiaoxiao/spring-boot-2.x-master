package com.jun.springbootmybatismultipledatasource.service.business.impl;

import com.jun.springbootmybatismultipledatasource.dao.mapper.db1.bo.TestUser;
import com.jun.springbootmybatismultipledatasource.dao.mapper.db2.bo.TestBook;
import com.jun.springbootmybatismultipledatasource.service.atom.interfaces.TestBookAtomSV;
import com.jun.springbootmybatismultipledatasource.service.atom.interfaces.TestUserAtomSV;
import com.jun.springbootmybatismultipledatasource.service.business.interfaces.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Autowired
    private TestUserAtomSV testUserAtomSV;

    @Autowired
    private TestBookAtomSV testBookAtomSV;

    @Override
    public void test1() {
        TestBook req1 = new TestBook();
        req1.setBookId(1L);
        List<TestBook> testBookList =  testBookAtomSV.queryTestBook(req1);

        TestUser req2 = new TestUser();
        req2.setUserId(1L);
        List<TestUser> testUserList = testUserAtomSV.queryTestUser(req2);

    }

    @Override
    public void test2() {
        TestBook req1 = new TestBook();
        req1.setBookName("数学");
        req1.setBookPrice("20");
        testBookAtomSV.addTestBook(req1);

        TestUser req2 = new TestUser();
        req2.setUserName("小明");
        req2.setUserAge("18");
        testUserAtomSV.addTestUser(req2);
    }

    @Override
    public void test3() {

    }
}
