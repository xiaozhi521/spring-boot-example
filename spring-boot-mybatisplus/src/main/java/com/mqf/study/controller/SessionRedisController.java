package com.mqf.study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @ClassName SessionRedisController
 * @Description TODO
 * @Author mqf
 * @Date 2019/3/15 12:58
 */
@RestController
public class SessionRedisController {

    @GetMapping("/getSession")
    public String getSession(HttpSession session){
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        System.out.println(uid);
        System.out.println(session.getMaxInactiveInterval());
        return session.getId();
    }
}
