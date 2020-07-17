package com.itdan.spring_demo.aop.trancetion.service.impl;

import com.itdan.spring_demo.aop.trancetion.dao.LogDao;
import com.itdan.spring_demo.aop.trancetion.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LogServiceImpl implements ILogService {
	@Autowired
	private LogDao logDao;

    /**
     *  * 事务的传播行为
     * 		 * Spring中事务的定义：
     *          * Propagation（key属性确定代理应该给哪个方法增加事务行为。这样的属性最重要的部份是传播行为。）有以下选项可供使用：
     *          * PROPAGATION_REQUIRED—如果当前有事务，就用当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。
     *          * PROPAGATION_SUPPORTS--支持当前事务，如果当前没有事务，就以非事务方式执行。//如果外层方法没有事务，就会以非事务进行执行。
     *          * PROPAGATION_MANDATORY--支持当前事务，如果当前没有事务，就抛出异常。 
     *          * PROPAGATION_REQUIRES_NEW--新建事务，如果当前存在事务，把当前事务挂起。 
     *          * PROPAGATION_NOT_SUPPORTED--以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
     *          * PROPAGATION_NEVER--以非事务方式执行，如果当前存在事务，则抛出异常。
     *          * 默认传播行为为REQUIRED
     */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void addLog() {
		logDao.add("log_" + System.currentTimeMillis());
	}

}
