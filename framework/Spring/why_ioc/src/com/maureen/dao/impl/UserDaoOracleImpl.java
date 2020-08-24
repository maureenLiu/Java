package com.maureen.dao.impl;

import com.maureen.dao.UserDao;

public class UserDaoOracleImpl implements UserDao {
    @Override
    public void getUser() {
        System.out.println("从oracle中获取数据");
    }
}
