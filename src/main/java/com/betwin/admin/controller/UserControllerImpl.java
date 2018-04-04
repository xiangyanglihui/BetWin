package com.betwin.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.betwin.admin.service.IUserService;

@RestController
@RequestMapping(path = "/user")
public class UserControllerImpl implements IUserController {

    @Autowired
    private IUserService userService;
    
    @RequestMapping(path="/{id}",method = RequestMethod.GET)
    @Override
    public String getUser(@PathVariable long id) {
        return userService.getUser(id).toString();
    }

}
