package com.jun.springbootmybatismultipledatasource.dao.mapper.db2.interfaces;

import com.jun.springbootmybatismultipledatasource.dao.mapper.db2.bo.TestBook;
import com.jun.springbootmybatismultipledatasource.dao.mapper.db2.bo.TestBookCriteria;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestBookMapper {
    int countByExample(TestBookCriteria example);

    int deleteByExample(TestBookCriteria example);

    int deleteByPrimaryKey(Long bookId);

    int insert(TestBook record);

    int insertSelective(TestBook record);

    List<TestBook> selectByExample(TestBookCriteria example);

    TestBook selectByPrimaryKey(Long bookId);

    int updateByExampleSelective(@Param("record") TestBook record, @Param("example") TestBookCriteria example);

    int updateByExample(@Param("record") TestBook record, @Param("example") TestBookCriteria example);

    int updateByPrimaryKeySelective(TestBook record);

    int updateByPrimaryKey(TestBook record);
}