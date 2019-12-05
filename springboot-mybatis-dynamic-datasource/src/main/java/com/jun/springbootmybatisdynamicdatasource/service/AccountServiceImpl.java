package com.jun.springbootmybatisdynamicdatasource.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jun.springbootmybatisdynamicdatasource.dao.mapper.AccountMapper;
import com.jun.springbootmybatisdynamicdatasource.entity.Account;
import com.jun.springbootmybatisdynamicdatasource.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author JZxiaoxiao
 * @since 2019-12-05
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @DS("slave_1")
    @Override
    public List<Account> getList(String id) {
        return this.baseMapper.selectOne123(id);
    }

}
