package com.jun.springbootstorm.dao.mapper.interfaces;

import com.jun.springbootstorm.dao.mapper.bo.TestUser;
import com.jun.springbootstorm.dao.mapper.bo.TestUserCriteria;
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