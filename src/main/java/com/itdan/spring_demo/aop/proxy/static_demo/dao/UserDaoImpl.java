package com.itdan.spring_demo.aop.proxy.static_demo.dao;

public class UserDaoImpl  implements IUserDao{

    @Override
    public boolean addUser() {
        System.out.println("添加客户成功");
        return true;
    }
}
