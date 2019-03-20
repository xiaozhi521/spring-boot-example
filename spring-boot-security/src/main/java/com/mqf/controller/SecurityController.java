package com.mqf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SecurityController
 * @Description TODO
 * @Author mqf
 * @Date 2019/3/19 18:27
 */
@Controller
public class SecurityController {

    private final String prefix = "pages/";

    @GetMapping("/")
    public String index(){
        return "welcome";
    }
    //level1 映射
    @GetMapping("/level1/{path}")
    public String level1Controller(@PathVariable("path") String path){
        return prefix + "/level1/" + path;
    }
    //level2 映射
    @GetMapping("/level2/{path}")
    public String level2Controller(@PathVariable("path") String path){
        return prefix + "/level2/" + path;
    }
    //level3 映射
    @GetMapping("/level3/{path}")
    public String level3Controller(@PathVariable("path") String path){
        return prefix + "/level3/" + path;
    }

    /**
     * 登陆页
     * @return
     */
    @GetMapping("/userLogin")
    public String loginPage() {
        return prefix+"login";
    }

}
