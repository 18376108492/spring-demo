package com.itdan.spring_demo.ioc.annotation.service.impl;

import com.itdan.spring_demo.ioc.annotation.annotation.DanService;
import com.itdan.spring_demo.ioc.annotation.service.IUserService;

@DanService
public class UserServiceImpl implements IUserService {
    @Override
    public void add() {
        System.out.println("调用添加方法");
    }
}
