package com.itdan.spring_demo.aop.trancetion.test;

import com.itdan.spring_demo.aop.trancetion.service.IUserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TrancetionalTest01 {


        public static void main(String[] args) {
            ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
            IUserService userService = (IUserService) applicationContext.getBean("userServiceImpl");
            userService.add();
        }

}
