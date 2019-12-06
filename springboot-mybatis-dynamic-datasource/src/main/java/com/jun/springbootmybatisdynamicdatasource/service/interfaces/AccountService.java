package com.jun.springbootmybatisdynamicdatasource.service.interfaces;

import com.jun.springbootmybatisdynamicdatasource.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author JZxiaoxiao
 * @since 2019-12-05
 */
public interface AccountService extends IService<Account> {
    List<Account> getList(String id);

    List<Account> getAccountList(String id);
}
