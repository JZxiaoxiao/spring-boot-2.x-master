package com.jun.springbootmybatisdynamicdatasource.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jun.springbootmybatisdynamicdatasource.dao.mapper.AccountMapper;
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
    private AccountMapper accountMapper;

    @Autowired
    private TestAccountMapper testAccountMapper;

    @Override
    public String query(String id) {
        //直接调用Mybatis plus 的service方法AOP不会生效，即数据源不会动态切换!!!
        List<Account> accountList = accountService.list(new QueryWrapper<Account>().lambda()
                .eq(Account::getItemID, id));
        Account account = accountList.get(0);
        return "name="+account.getName()+",age="+account.getAge();
    }

    @Override
    public String query2(String id) {
        //直接调用Mybatis plus 的service方法AOP不会生效，即数据源不会动态切换!!!
        List<Account> accountList = accountService.getList(id);
        Account account = accountList.get(0);
        return "name="+account.getName()+",age="+account.getAge();
    }


    @Override
    public String query3(String id) {
        List<Account> accountList = testAccountMapper.selectAll();
        Account account = accountList.get(0);
        return "name="+account.getName()+",age="+account.getAge();
    }

}
