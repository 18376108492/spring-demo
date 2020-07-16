package com.itdan.spring_demo.aop.proxy.dynamic_demo.cjlb.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CjlibProxy implements MethodInterceptor {

    private Object target;//目标代理对象

    /**
     * 获取代理对象方法
     * @param target
     * @return
     */
    public Object getInstance(Object target) {
        this.target = target;
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("CJLB代理---开启事务");
        Object invoke = method.invoke(target, objects);
        System.out.println("CJLB代理---提交事务");
        return invoke;
    }
}
