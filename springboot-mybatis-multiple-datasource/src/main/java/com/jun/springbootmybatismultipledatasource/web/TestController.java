package com.jun.springbootmybatismultipledatasource.web;

import com.jun.springbootmybatismultipledatasource.service.business.interfaces.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;

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
}
