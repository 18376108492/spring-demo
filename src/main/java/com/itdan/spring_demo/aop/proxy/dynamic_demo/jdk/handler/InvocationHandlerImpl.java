package com.itdan.spring_demo.aop.proxy.dynamic_demo.jdk.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 通过实现InvocationHandler接口，实现自动的调用处理器
 */
public class InvocationHandlerImpl implements InvocationHandler {

    private Object tager;//目标代理对象

    public InvocationHandlerImpl(Object tager) {
        this.tager = tager;
    }

    /**
     *  代理处理
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开启事务");
        Object invoke = method.invoke(tager, args);
        System.out.println("提交事务");
        return invoke;
    }
}
