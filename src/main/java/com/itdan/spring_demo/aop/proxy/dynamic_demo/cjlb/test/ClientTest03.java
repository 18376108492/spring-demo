package com.itdan.spring_demo.aop.proxy.dynamic_demo.cjlb.test;

import com.itdan.spring_demo.aop.proxy.dynamic_demo.cjlb.proxy.CjlibProxy;
import com.itdan.spring_demo.aop.proxy.static_demo.dao.IUserDao;
import com.itdan.spring_demo.aop.proxy.static_demo.dao.UserDaoImpl;

import java.beans.Transient;

public class ClientTest03 {

    /**
     * java动态代理是利用反射机制生成一个实现代理接口的匿名类，在调用具体方法前调用InvokeHandler来处理。
     *     而cglib动态代理是利用asm开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。
     *     Spring中。
     * 1、如果目标对象实现了接口，默认情况下会采用JDK的动态代理实现AOP
     * 2、如果目标对象实现了接口，可以强制使用CGLIB实现AOP
     * 3、如果目标对象没有实现了接口，必须采用CGLIB库，spring会自动在JDK动态代理和CGLIB之间转换
     *     JDK动态代理只能对实现了接口的类生成代理，而不能针对类 。
     *     CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法 。
     *     因为是继承，所以该类或方法最好不要声明成final ，final可以阻止继承和多态。
     *
     * 也就是说CJLB可以没有IUserDao接口，而JDK代理必须要要有IUserDao接口
     */

    public static void main(String[] args) {
        UserDaoImpl userDaoImpl = new UserDaoImpl();
        CjlibProxy cjlibProxy = new CjlibProxy();
        UserDaoImpl instance =(UserDaoImpl) cjlibProxy.getInstance(userDaoImpl);
        instance.addUser();
    }

}
