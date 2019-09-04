package com.jun.springbootmybatismultipledatasource.service.business.interfaces;

public interface TestService {

    void test1();

    void test2();

    void testTransaction1() throws Exception;

    void testTransaction2() throws Exception;
}
