package com.jun.springbootmybatisdynamicdatasource.web;

import com.jun.springbootmybatisdynamicdatasource.service.interfaces.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author JZxiaoxiao
 * @Description
 * @Date 2019/12/5 14:39
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/query")
    public String testQuery(@RequestParam String id){
        String s = testService.query(id);
        return "success!"+s;
    }

    @GetMapping("/query1")
    public String testQuery1(@RequestParam String id){
        String s = testService.query1(id);
        return "success!"+s;
    }

    @GetMapping("/query2")
    public String testQuery2(@RequestParam String id){
        String s = testService.query2(id);
        return "success!"+s;
    }

    @GetMapping("/query3")
    public String testQuery3(@RequestParam String id){
        String s = testService.query3(id);
        return "success!"+s;
    }
}
