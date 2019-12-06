package com.jun.springbootmybatisdynamicdatasource.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jun.springbootmybatisdynamicdatasource.dao.mapper.TestAccountMapper;
import com.jun.springbootmybatisdynamicdatasource.entity.Account;
import com.jun.springbootmybatisdynamicdatasource.service.interfaces.AccountService;
import com.jun.springbootmybatisdynamicdatasource.service.interfaces.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author JZxiaoxiao
 * @Description
 * @Date 2019/12/5 15:32
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TestAccountMapper testAccountMapper;

    /**
     * @Author JZxiaoxiao
     * @Description
     *  AOP要生效需要满足两个条件：
     *  1.mybatis-plus-boot-starter 需要放开
     *  2.添加mybatis-plus:mapper-locations 扫描
     */
    @Override
    public String query(String id) {
        //直接调用Mybatis plus 的service方法AOP不会生效，即数据源不会动态切换!!!
        List<Account> accountList = accountService.list(new QueryWrapper<Account>().lambda()
                .eq(Account::getItemID, id));
        Account account = accountList.get(0);
        return "name="+account.getName()+",age="+account.getAge();
    }
    /**
     * @Author JZxiaoxiao
     * @Description
     *  AOP要生效需要满足两个条件：
     *  1.mybatis-plus-boot-starter 需要放开
     *  2.添加mybatis-plus:mapper-locations 扫描
     */
    @Override
    public String query1(String id) {
        //调用Mybatis plus 重写的service方法AOP会生效，数据源会动态切换!!!
        List<Account> accountList = accountService.getAccountList(id);
        Account account = accountList.get(0);
        return "name=" + account.getName() + ",age=" + account.getAge();
    }

    /**
     * @Author JZxiaoxiao
     * @Description
     *  AOP要生效需要满足两个条件：
     *  1.mybatis-spring-boot-starter 需要放开
     *  2.添加mybatis:mapper-locations 扫描
     */
    @Override
    public String query2(String id) {
        //调用Mybatis plus 自己写的service方法AOP会生效，数据源会动态切换!!!
        List<Account> accountList = accountService.getList(id);
        Account account = accountList.get(0);
        return "name=" + account.getName() + ",age=" + account.getAge();
    }

    /**
     * @Author JZxiaoxiao
     * @Description
     *  AOP要生效需要满足两个条件：
     *  1.mybatis-plus-boot-starter 需要去掉
     *  2.添加mybatis:mapper-locations 扫描
     *  3.mybatis-spring-boot-starter 需要放开
     */
    @Override
    public String query3(String id) {
        //调用Mybatis 原生方法AOP会生效，数据源会动态切换!!!
        Account account = new Account();
        account.setItemID(id);
        account.setName("hhh");
        testAccountMapper.insertOne123(account);
        return "name="+account.getName()+",age="+account.getAge();
    }

}
