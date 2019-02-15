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
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

//   http://localhost:8080/user/updateUser?id=2&name=mqf&age=18&email=test3@163.com
    @RequestMapping("/updateUser")
    public User updateUser(User user){
//        boolean update = userService.updateById(user);
//        System.out.println(user);
        return userService.updateUser(user);
    }

    //   http://localhost:8080/user/isnertUser?name=mqf&age=18&email=test3@163.com
    @RequestMapping("/insertUser")
    public User insertUser(User user){
        //基本模式
//        userService.save(user);
        //AR模式
//        user.insert();
//        System.out.println(user);
        return userService.insertUser(user);
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
//        System.out.println("id : " + id);
//        boolean removeById = userService.removeById(id);
//        return removeById ? " delete success !!!" : "delete fail !!!";

        return userService.deleteUser(id) == 1 ? " delete success !!!" : "delete fail !!!";
    }

    //http://localhost:8080/user/getUserByName/Billie
    @GetMapping("/getUserByName/{name}")
    public User getUserByName(@PathVariable("name") String name){
//        User user = new User();
//        List<User> userList = user.selectList(new QueryWrapper<User>().like("name", name));
//        return userList.get(0);
        return userService.getUserByName(name);
    }
}
