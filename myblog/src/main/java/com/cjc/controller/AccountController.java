package com.cjc.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjc.common.lang.Result;
import com.cjc.entity.LoginVO;
import com.cjc.entity.User;
import com.cjc.service.UserService;
import com.cjc.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: cjc
 * Date: 2021/2/23
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 **/
@RestController
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(
            @Validated @RequestBody LoginVO loginVO,
            HttpServletResponse response){

        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginVO.getUsername()));
        Assert.notNull(user,"账号或密码不正确");
        if(!user.getPassword().equals(SecureUtil.md5(loginVO.getPassword()))){
            return Result.fail("账号或密码不正确");
        }
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expose-Headers","Authorization");
        return Result.succ(MapUtil.builder()
                .put("id",user.getId())
                .put("username",user.getUsername())
                .put("avatar",user.getAvatar())
                .put("email",user.getEmail())
                .map());
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

}
