package com.damao.bilibilishop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 路由控制
 * @author 呆毛
 */
@Controller
public class RouterController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
}
