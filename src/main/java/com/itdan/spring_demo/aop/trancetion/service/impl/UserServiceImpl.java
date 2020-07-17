package com.itdan.spring_demo.aop.trancetion.service.impl;

import com.itdan.spring_demo.aop.trancetion.annotation.DanTranctional;
import com.itdan.spring_demo.aop.trancetion.dao.LogDao;
import com.itdan.spring_demo.aop.trancetion.dao.UserDao;
import com.itdan.spring_demo.aop.trancetion.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



//user 服务层
@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private LogDao logDao;

	@DanTranctional
	@Override
	public void add() {
		logDao.add("test001");
		userDao.add("test001", 20);
		//int i = 1 / 0;
		System.out.println("################");
		userDao.add("test002", 21);
	}


}
