package com.itdan.spring_demo.aop.trancetion.service.impl;

import com.itdan.spring_demo.aop.trancetion.annotation.DanTranctional;
import com.itdan.spring_demo.aop.trancetion.dao.LogDao;
import com.itdan.spring_demo.aop.trancetion.dao.UserDao;
import com.itdan.spring_demo.aop.trancetion.service.ILogService;
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

	@Autowired
	private ILogService iLogService;

	@DanTranctional
	@Override
	public void add() {
		/*
		 *日志记录每次都会插入，就算是抛出异常也应当不会发生回滚
		 * 解决的办法: 调用ILogService中的方法，将其分为两个不同的事务
		 * 日志这种功能一般都会用AOP去方法调用前去做处理，这里只是举一个例子
		 */
		//logDao.add("test003");
		iLogService.addLog();
		userDao.add("test003", 20);
		int i = 1 / 0;
		System.out.println("################");
		userDao.add("test004", 21);
	}


}
