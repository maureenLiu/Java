package com.maureen.dao.impl;

import com.maureen.dao.UserDao;

//从Mysql中获取数据
public class UserDaoMysqlImpl implements UserDao {
    @Override
    public void getUser() {
        System.out.println("从mysql获取数据");
    }
}
