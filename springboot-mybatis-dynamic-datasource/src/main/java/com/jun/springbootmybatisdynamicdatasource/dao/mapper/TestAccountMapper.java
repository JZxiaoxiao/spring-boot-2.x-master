package com.jun.springbootmybatisdynamicdatasource.dao.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.jun.springbootmybatisdynamicdatasource.entity.Account;

/**
 * @Author JZxiaoxiao
 * @Description
 * @Date 2019/12/5 16:03
 */
@DS("slave")
public interface TestAccountMapper {

    @DS("slave_1")
    int insertOne123(Account account);
}
