package com.jun.springbootmybatisdynamicdatasource.dao.mapper;

import com.jun.springbootmybatisdynamicdatasource.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author JZxiaoxiao
 * @since 2019-12-05
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    List<Account> selectOne123(String itemID);
}
