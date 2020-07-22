package com.itdan.spring_demo.db_pool.bean;

/**
 * 数据库连接池信息
 */
public class PoolConfig {

	private String driverName;
	private String url;
	private String userName;
	private String passWord;

	/**
	 * 空闲连接数、空闲最大连接数、初始化连接数
	 */
	private int minCon = 5;
	private int maxCon = 5;
	private int initCon = 5;
	/**
	 * 连接池允许最大活动连接数
	 */
	private int maxActiveConn = 10;
	/**
	 * 连接不够时,等待时间,单位为毫秒
	 */
	private int waitTime = 1000;

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public int getMinCon() {
		return minCon;
	}

	public void setMinCon(int minCon) {
		this.minCon = minCon;
	}

	public int getMaxCon() {
		return maxCon;
	}

	public void setMaxCon(int maxCon) {
		this.maxCon = maxCon;
	}

	public int getInitCon() {
		return initCon;
	}

	public void setInitCon(int initCon) {
		this.initCon = initCon;
	}

	public int getMaxActiveConn() {
		return maxActiveConn;
	}

	public void setMaxActiveConn(int maxActiveConn) {
		this.maxActiveConn = maxActiveConn;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
}
