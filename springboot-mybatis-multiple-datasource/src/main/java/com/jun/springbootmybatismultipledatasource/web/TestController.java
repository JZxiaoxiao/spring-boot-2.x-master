package com.jun.springbootmybatismultipledatasource.web;

import com.jun.springbootmybatismultipledatasource.service.business.interfaces.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @Description:
 * 测试Controller
 * @author JZxiaoxiao
 * @date 2019年9月1日
 */
@RestController()
@RequestMapping("/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    public TestService testService;

    @RequestMapping("/add")
    public String set() {
        testService.test2();
        return "Hello World";
    }

    @RequestMapping("/show")
    public String show(){
        testService.test1();
        return "Hello World";
    }
    /**
     * 单事务
     * @return
     */
    @RequestMapping("/add2")
    public String set2() {
        try{
            testService.testTransaction1();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "Hello World";
    }

    /**
     * 分布式事务
     * @return
     */
    @RequestMapping("/add3")
    public String set3() {
        try{
            testService.testTransaction2();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "Hello World";
    }
}
