package com.jun.springbootmybatisdynamicdatasource.dao.mapper;

import com.jun.springbootmybatisdynamicdatasource.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author JZxiaoxiao
 * @since 2019-12-05
 */
public interface AccountMapper extends BaseMapper<Account> {

    List<Account> selectOne123(String itemID);
}
