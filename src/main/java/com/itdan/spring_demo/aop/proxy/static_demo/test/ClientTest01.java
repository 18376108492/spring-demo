package com.itdan.spring_demo.aop.proxy.static_demo.test;

import com.itdan.spring_demo.aop.proxy.static_demo.dao.UserDaoImpl;
import com.itdan.spring_demo.aop.proxy.static_demo.proxy.UserDaoProxy;

public class ClientTest01 {

    /**
     *  静态代理测试类
     *
     *  通常调用UserDao的添加方法时，直接创建其实现类去调用方法
     *  现在我想在该方法调用之前开启事务和方法结束之后关闭事务，
     *  而且该开启和关闭事务的操作增删改都需要使用到,可是每一个方法都在前后加事务处理，代码太庸肿和不易维护
     *  所以我们可以给其写一个代理类来帮我们处理.
     *
     *  静态代理方法需要代理实现类，每实现一个方法都要去做代理处理，所以不建议使用静态代理，可以转换成动态代理方式
     */
    public static void main(String[] args) {

        UserDaoProxy userDaoProxy=new UserDaoProxy(new UserDaoImpl());

        userDaoProxy.addUser();
    }
}
