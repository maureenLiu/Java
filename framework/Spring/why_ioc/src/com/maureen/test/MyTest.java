package com.maureen.test;

import com.maureen.dao.UserDao;
import com.maureen.dao.impl.UserDaoMysqlImpl;
import com.maureen.dao.impl.UserDaoOracleImpl;
import com.maureen.service.UserService;
import com.maureen.service.impl.UserServiceImpl;

public class MyTest {
    public static void main(String[] args) {
//        UserService userService = new UserServiceImpl();
//        userService.getUser();

        UserServiceImpl userService = new UserServiceImpl();

        UserDao userDao = new UserDaoMysqlImpl();
        userService.setUserDao(userDao);
        userService.getUser();

        System.out.println("-------------------");

        UserDao userDao1 = new UserDaoOracleImpl();
        userService.setUserDao(userDao1);
        userService.getUser();
    }
}
