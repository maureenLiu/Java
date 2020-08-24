package com.maureen.dao.impl;

import com.maureen.dao.UserDao;

//未指定从哪里获取数据
public class UserDaoImpl implements UserDao {
    @Override
    public void getUser() {
        System.out.println("获取用户");
    }
}
