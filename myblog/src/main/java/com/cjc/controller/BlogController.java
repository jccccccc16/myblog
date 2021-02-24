package com.cjc.controller;


import cn.hutool.core.lang.Assert;
import cn.hutool.db.Page;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cjc.common.lang.Result;
import com.cjc.entity.Blog;
import com.cjc.service.BlogService;
import com.cjc.util.ShiroUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2021-02-21
 */
@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){

        Page page = new Page(currentPage, 5);
        IPage<Blog> pageInfo = blogService.page((IPage<Blog>) page, new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.succ(pageInfo);
    }


    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable("id") Integer id){



        return Result.succ(null);


    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog){

        Blog temp = null;



        if(blog.getId()!=null){
            // 更新状态
            temp = blogService.getById(blog.getId());

            // 判断是否是自己的文章
            Assert.isTrue(temp.getUserId() == ShiroUtils.getProfile().getId());

        }else{
            // 添加状态
            temp = new Blog();
            temp.setUserId(ShiroUtils.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }

        BeanUtils.copyProperties(blog,temp,"id","userId","created","status");
        blogService.saveOrUpdate(temp);


        return Result.succ(null);


    }





}
