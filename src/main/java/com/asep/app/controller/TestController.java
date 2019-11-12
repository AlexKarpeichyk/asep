package com.asep.app.controller;

import com.asep.app.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/test")
    public String message() {
        return "Hello Spring Mongo API";
    }

}
