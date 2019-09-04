package com.jun.springbootmybatismultipledatasource.dao.mapper.db1.interfaces;

import com.jun.springbootmybatismultipledatasource.dao.mapper.db1.bo.TestUser;
import com.jun.springbootmybatismultipledatasource.dao.mapper.db1.bo.TestUserCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestUserMapper {
    int countByExample(TestUserCriteria example);

    int deleteByExample(TestUserCriteria example);

    int deleteByPrimaryKey(Long userId);

    int insert(TestUser record);

    int insertSelective(TestUser record);

    List<TestUser> selectByExample(TestUserCriteria example);

    TestUser selectByPrimaryKey(Long userId);

    int updateByExampleSelective(@Param("record") TestUser record, @Param("example") TestUserCriteria example);

    int updateByExample(@Param("record") TestUser record, @Param("example") TestUserCriteria example);

    int updateByPrimaryKeySelective(TestUser record);

    int updateByPrimaryKey(TestUser record);
}