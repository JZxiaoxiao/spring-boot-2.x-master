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
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @Description:
 * 测试类
 * Spring建议是在具体的类（或类的方法）上使用 @Transactional 注解
 * @author JZxiaoxiao
 * @date 2019年9月1日
 */
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

    /**
     * 单数据源事务支持
     */
    // 若是只指定db2 则不支持事务 @Transactional(transactionManager = "db2TransactionManager",rollbackFor = Exception.class)
    @Transactional(transactionManager = "db1TransactionManager",rollbackFor = Exception.class)
    @Override
    public void testTransaction1() throws Exception{
        TestUser req2 = new TestUser();
        req2.setUserName("小wang");
        req2.setUserAge("18");
        testUserAtomSV.addTestUser(req2);
        throw new FileNotFoundException("测试一下");
    }

    /**
     * 多数据源分布式事务,不需要指定transactionManager
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void testTransaction2() throws Exception{
        TestUser req2 = new TestUser();
        req2.setUserName("小wang");
        req2.setUserAge("18");
        testUserAtomSV.addTestUser(req2);

        TestBook req1 = new TestBook();
        req1.setBookName("语文");
        req1.setBookPrice("20");
        testBookAtomSV.addTestBook(req1);

        throw new FileNotFoundException("测试一下");
    }
}
