package com.maureen.service.impl;

import com.maureen.dao.UserDao;
import com.maureen.dao.impl.UserDaoImpl;
import com.maureen.dao.impl.UserDaoMysqlImpl;
import com.maureen.dao.impl.UserDaoOracleImpl;
import com.maureen.service.UserService;

public class UserServiceImpl implements UserService {

//    1、未指定数据来源
//    private UserDao userDao = new UserDaoImpl();

//    2、如果要从mysql获取数据，就要修改为如下：
//    private UserDao userDao = new UserDaoMysqlImpl();

//    3、如果要从oracle获取数据，就要修改为如下：
//    private UserDao userDao = new UserDaoOracleImpl();

//    如果有上百个实现类，那么这个地方的代码就得不断更改，非常麻烦。
// ==========================换种写法：用set方法
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void getUser() {
        userDao.getUser();
    }
}
