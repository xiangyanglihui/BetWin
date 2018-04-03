package com.betwin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/hi")
public class HelloController {

    @RequestMapping(method = RequestMethod.GET)
    public String sayHello() {
        return "Hello..........";
    }
    
}
