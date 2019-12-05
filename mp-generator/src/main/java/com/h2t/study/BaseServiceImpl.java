package com.h2t.study;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Collection;
import java.util.List;

/**
 * @Description: 基本业务实现类
 *
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    /**
     * 添加
     *
     * @param obj
     * @return
     * */
    @Override
    public boolean insert(T obj) {
        return this.save(obj);
    }

    /**
     * 批量添加
     *
     * @param objList
     * @return
     * */
    @Override
    public boolean insertBatch(List<T> objList) {
        return this.saveBatch(objList);
    }


    /**
     * 根据id删除
     *

     * @param obj
     */
    @Override
    public boolean modifyById(T obj) {
        return this.updateById(obj);
    }

    /**
     * 根据传入参数条件进行删除
     *
     * @param obj
     * @return
     * */
    @Override
    public boolean delete(T obj) {
        Wrapper wrapper = new QueryWrapper<>(obj);
        return this.remove(wrapper);
    }

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * */
    @Override
    public boolean deleteByIds(List<Long> idList) {
        return this.removeByIds(idList);
    }

    /**
     * 批量id删除
     *
     * @param id
     * @return
     * */
    @Override
    public boolean deleteById(Long id) {
        return this.removeById(id);
    }

    /**
     * 根据id进行更新
     *
     * @param obj
     * @return
     * */
//    @Override
//    public boolean modify(T obj) {
//        Wrapper wrapper  = new QueryWrapper<>(obj);
//        return this.update(wrapper);
//    }

    /**
     * 根据id查找
     *

     * @param id
     */
    @Override
    public T selectById(Long id) {
        return this.getById(id);
    }

    /**
     * 根据条件进行查询
     *
     * @param obj
     * @return
     * */
    @Override
    public List<T> selectList(T obj) {
        Wrapper wrapper = new QueryWrapper<>(obj);
        return this.list(wrapper);
    }

    /**
     * 根据id批量查询
     *
     * @param idList
     * @return
     * */
    @Override
    public Collection<T> selectByIds(List<Long> idList) {
        return this.listByIds(idList);
    }

    /**
     * 分页查询
     *
     * @param pageNo 页码
     * @param pageSize 页数
     * @param obj
     * @return
     * */
    @Override
    public IPage<T> selectPage(T obj, Integer pageNo, Integer pageSize) {
        Page<T> page = new Page<>(pageNo, pageSize);
        Wrapper wrapper = new QueryWrapper<>(obj);
        return this.page(page, wrapper);
    }
}
