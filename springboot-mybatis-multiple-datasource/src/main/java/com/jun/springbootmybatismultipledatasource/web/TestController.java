package com.jun.springbootmybatismultipledatasource.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);


    @RequestMapping("/show1")
    public String set() {
        return "Hello World";
    }

    @RequestMapping("/show2")
    public String show(){
        return "Hello World";
    }
}
