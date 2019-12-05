package com.jun.springbootmybatisdynamicdatasource.dao.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.jun.springbootmybatisdynamicdatasource.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author JZxiaoxiao
 * @Description
 * @Date 2019/12/5 16:03
 */
@Mapper
public interface TestAccountMapper {

    @DS("slave_1")
    @Select("SELECT * FROM account")
    List<Account> selectAll();
}
