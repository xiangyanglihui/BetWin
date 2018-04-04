package com.betwin.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.betwin.service.Web3jService;

@RestController
@RequestMapping(path = "/hi")
public class HelloController {

    @Autowired
    private Web3jService web3jService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String sayHello() throws IOException {
        return web3jService.getClientVersion();
    }
    
}
