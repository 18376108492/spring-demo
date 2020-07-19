package com.itdan.spring_demo.ioc.annotation.test;

import com.itdan.spring_demo.ioc.annotation.service.IUserService;
import com.itdan.spring_demo.ioc.annotation.utils.MyClassPathApplicationContext;

/**
 * IOC 注解版测试用例
 */
public class IocAnnotationTest {

    public static void main(String[] args) throws Exception {
        MyClassPathApplicationContext context=new MyClassPathApplicationContext("com.itdan.spring_demo.ioc.annotation.service");
        IUserService iUserService =(IUserService) context.getBean("userServiceImpl");
        iUserService.add();
    }
}
