package com.jun.springbootredis.web;

import com.jun.springbootredis.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @Author JZxiaoxiao
 * @Description
 * @Date 2019/11/12 16:51
 * @Param 
 * @return 
 */
@Api(tags = "测试")
@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/name")
    @ApiOperation(value = "查询传参", notes = "查询传参")
    public String getName(@RequestParam(value = "name", defaultValue = "defaultValue") String name) {
        return "hello" + name;
    }

    @PostMapping("/add")
    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    public String postUser(@RequestBody User user) {
        return "success";
    }

    @PutMapping("/updateUser")
    @ApiOperation(value = "更新用户", notes = "根据id更新用户")
    public String updateUser(@RequestParam(value = "id") String id) {
        User u = new User();
        u.setId(Long.parseLong(id));
        return "success:"+id;
    }

    @DeleteMapping("/deleteUser")
    @ApiOperation(value = "删除用户", notes = "根据id删除用户")
    public String deleteUser(@RequestParam(value = "id") String id) {
        return "success:"+id;
    }
}


