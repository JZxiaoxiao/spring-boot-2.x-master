package com.jun.springbootmybatismultipledatasource.service.atom.interfaces;

import com.jun.springbootmybatismultipledatasource.dao.mapper.db2.bo.TestBook;

import java.util.List;

public interface TestBookAtomSV {

    int addTestBook(TestBook req);

    List<TestBook> queryTestBook(TestBook req);
}
