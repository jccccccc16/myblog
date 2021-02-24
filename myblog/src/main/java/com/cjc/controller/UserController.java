package com.cjc.controller;


import com.cjc.common.lang.Result;
import com.cjc.entity.User;
import com.cjc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2021-02-21
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public Object index(){
       return  userService.getById(1);
    }

    @RequestMapping("/save")
    public Result save(@Validated @RequestBody User user){
        return Result.succ(user);
    }

}
