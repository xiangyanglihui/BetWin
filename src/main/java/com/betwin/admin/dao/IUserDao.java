package com.betwin.admin.dao;

import com.betwin.admin.model.UserEntity;

public interface IUserDao {
    
    public UserEntity find(long id);
}
