package com.jun.springbootdocker.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jun
 * @version 1.0
 * @ClassName DockerController
 * @Description TODO
 * @date 2019/11/13 15:50
 */
@RestController
public class DockerController {

    @RequestMapping("/")
    public String index() {
        return "Hello Docker!";
    }
}