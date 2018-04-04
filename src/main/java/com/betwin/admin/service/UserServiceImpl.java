package com.betwin.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betwin.admin.dao.IUserDao;
import com.betwin.admin.model.UserEntity;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;
    
    @Override
    public UserEntity getUser(long id) {
        return userDao.find(id);
    }

}
