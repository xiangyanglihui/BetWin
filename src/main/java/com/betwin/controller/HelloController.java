package com.betwin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/hi")
public class HelloController {

	@Value("${chain.ip}")
	private String chainIp;
	
    @RequestMapping(method = RequestMethod.GET)
    public String sayHello() {
        return "Hello.........." + chainIp;
    }
    
}
