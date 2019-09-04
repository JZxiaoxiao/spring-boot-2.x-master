package com.jun.springbootmybatismultipledatasource.service.atom.impl;

import com.jun.springbootmybatismultipledatasource.dao.mapper.db2.bo.TestBook;
import com.jun.springbootmybatismultipledatasource.dao.mapper.db2.bo.TestBookCriteria;
import com.jun.springbootmybatismultipledatasource.dao.mapper.factory.MapperFactory;
import com.jun.springbootmybatismultipledatasource.service.atom.interfaces.TestBookAtomSV;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class TestBookAtomSVImpl implements TestBookAtomSV {
    @Override
    public int addTestBook(TestBook req) {
        return MapperFactory.getTestBookMapper().insertSelective(req);
    }

    @Override
    public List<TestBook> queryTestBook(TestBook req) {
        TestBookCriteria testBookCriteria = new TestBookCriteria();
        TestBookCriteria.Criteria criteria = testBookCriteria.createCriteria();
        criteria.andBookIdEqualTo(req.getBookId());
        List<TestBook> list = MapperFactory.getTestBookMapper().selectByExample(testBookCriteria);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list;
    }
}
