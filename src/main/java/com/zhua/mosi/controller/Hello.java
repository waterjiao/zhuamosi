package com.zhua.mosi.controller;

import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class Hello{
    @RequestMapping("/hello")
    public String hello(){
        return "index";
    }
}