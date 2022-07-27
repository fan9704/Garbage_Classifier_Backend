package com.bezkoder.spring.datajpa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/actuator")
@RestController
public class SystemController {
    @GetMapping("/work")
    public String work() throws InterruptedException{
        Thread.sleep(10*1000L);
        return "success";
    }
}
