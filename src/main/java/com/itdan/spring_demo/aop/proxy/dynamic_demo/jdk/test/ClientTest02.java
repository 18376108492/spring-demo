package com.itdan.spring_demo.aop.proxy.dynamic_demo.jdk.test;

import com.itdan.spring_demo.aop.proxy.dynamic_demo.jdk.handler.InvocationHandlerImpl;
import com.itdan.spring_demo.aop.proxy.static_demo.dao.IUserDao;
import com.itdan.spring_demo.aop.proxy.static_demo.dao.UserDaoImpl;

import java.lang.reflect.Proxy;

public class ClientTest02 {


    /**
     * JDK动态代理测试类
     *
     * 动态代理不需要写动态代理实现类，动态代理可以通过l类加载和接口去获取代理对象
     *
     */
    public static void main(String[] args) {
        IUserDao iUserDao = new UserDaoImpl();
        InvocationHandlerImpl invocationHandler=new InvocationHandlerImpl(iUserDao);
        ClassLoader classLoader = iUserDao.getClass().getClassLoader();//获取类加载器
        Class<?>[] interfaces = iUserDao.getClass().getInterfaces();//获取接口方法数组

        //调用动态代理实例
        IUserDao userDao=(IUserDao) Proxy.newProxyInstance(classLoader,interfaces,invocationHandler);
        userDao.addUser();
    }
}
