package com.itdan.spring_demo.aop.trancetion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


//@Service
//public class LogServiceImpl implements LogService {
//	@Autowired
//	private LogDao logDao;
//
//	@Transactional(propagation = Propagation.MANDATORY)
//	public void addLog() {
//		logDao.add("log_" + System.currentTimeMillis());
//	}
//
//}
