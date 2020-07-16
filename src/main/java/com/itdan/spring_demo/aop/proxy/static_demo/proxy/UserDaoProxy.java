package com.itdan.spring_demo.aop.proxy.static_demo.proxy;

import com.itdan.spring_demo.aop.proxy.static_demo.dao.IUserDao;

/**
 * 用户代理对象
 */
public class UserDaoProxy implements IUserDao {

    private IUserDao iUserDao;

    public UserDaoProxy(IUserDao iUserDao) {
        this.iUserDao=iUserDao;
    }

    @Override
    public boolean addUser() {
        System.out.println("开启事务");
        iUserDao.addUser();
        System.out.println("关闭事务");
        return true;
    }
}
