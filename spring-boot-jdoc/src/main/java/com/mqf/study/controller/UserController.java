package com.mqf.study.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户模块
 * 
 * @author lianghao
 *
 *         2017年3月27日
 */
@RestController
@RequestMapping("/user")
public class UserController {
   
    
    /**
     * 用于添加用户功能
     * @title 新增用户
     * @param username|用户名|String|必填
     * @param password|密码|String|必填
     * @respBody {"code":"100000","data":"","message":"新增成功"}
     */
    @RequestMapping("/add")
    public String add(){
       
        return "{\"code\":\"100000\",\"data\":\"\",\"message\":\"新增成功\"}";
    }
    
    
    /**
     * 用于删除用户功能
     * @title 删除用户
     * @param id|用户id|Intger|必填
     * @respBody {"code":"100000","data":"","message":"删除成功"}
     */
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    public String delete(){
       
        return "{\"code\":\"100000\",\"data\":\"\",\"message\":\"删除成功\"}";
    }
    
    /**
     * 通过用户id查询用户功能
     * @title 查询ID查用户
     * @respParam username|用户名|String|必填
     * @respParam password|密码|String|必填
     * @respBody {"code":"100000","data":{"password":"123456","username":"13811111111"},"message":"获取成功"}
     */
    @RequestMapping("/getUserById")
    public String getUserById(){
        return "{\"code\":\"100000\",\"data\":{\"password\":\"123456\",\"username\":\"13811111111\"},\"message\":\"获取成功\"}";
    }
    
}