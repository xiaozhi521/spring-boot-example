package com.mqf.study.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.UpdateChainWrapper;
import com.mqf.study.beans.User;
import com.mqf.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mqf
 * @since 2019-02-13
 */
@CacheConfig(cacheNames="emp") //抽取公共配置
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/{id}")
    //不缓存 id 为 1 记录
//    @Cacheable(value = "emp",keyGenerator = "getKeyGenerator",condition = "#id==1",unless = "#id==2")
//    @Cacheable(cacheNames = "user",key="#id")
    @Cacheable(/*value = "user", */ key="#id")
    public User getUserById(@PathVariable("id") Long id){
//        User user = userService.getById(id);
        User user = new User();
        user = user.selectById(id);
        return user;
    }


    /**
     * @CachePut : 既调用方法，有更新缓存数据
     * @return
     */
//   http://localhost:8080/user/updateUser?id=2&name=mqf&age=18&email=test3@163.com
    @RequestMapping("/updateUser")
    @CachePut(/*value = "user", */key = "#result.id")
    public User updateUser(User user){
        boolean update = userService.updateById(user);
        System.out.println(user);
        return user;
    }

    //   http://localhost:8080/user/isnertUser?name=mqf&age=18&email=test3@163.com
    @RequestMapping("/isnertUser")
    @CachePut(/*value = "user", */ key = "#result.id")
    public User isnertuser(User user){
        //基本模式
        userService.save(user);
        //AR模式
//        user.insert();
        System.out.println(user);
        return user;
    }

    @GetMapping("/delete/{id}")
    @CacheEvict(/*value = "user", */key = "#id")
    public String deleteUser(@PathVariable("id") Long id){
        System.out.println("id : " + id);
        boolean removeById = userService.removeById(id);
        return removeById ? " delete success !!!" : "delete fail !!!";
    }

    //http://localhost:8080/user/getUserByName/Billie
    @GetMapping("/getUserByName/{name}")
    //定义复杂的缓存规则
    @Caching(
            cacheable = {
                    @Cacheable(/*value = "user", */ key = "#name")
            },
            put = {
                    @CachePut(/*value = "user", */ key = "#result.id"),
                    @CachePut(/*value = "user", */ key = "#result.email")
            }
    )
    public User getUserByName(@PathVariable("name") String name){
        User user = new User();
        List<User> userList = user.selectList(new QueryWrapper<User>().like("name", name));
        return userList.get(0);
    }
}
