package com.betwin.admin.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import com.betwin.admin.model.UserEntity;

@Repository
public class UserDaoImpl implements IUserDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public UserEntity find(long id) {
        return mongoTemplate.findById(id, UserEntity.class);
    }

}
