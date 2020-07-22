package com.itdan.spring_demo.db_pool.utils;

import com.itdan.spring_demo.db_pool.bean.PoolConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Vector;

/**
 * 自定义数据库连接池
 *
 * 连接池配置核心参数:
 * 1.空闲线程: 没有被使用的连接数
 * 2.活动线程: 正在使用的连接数
 * ---------------------
 * 创建线程池步骤:
 * 1.初始化线程池（初始化空闲线程）
 * 2.调用getConnection()获取连接
 *   2.1 从freePool(空闲线程池）中获取连接对象
 *   2.2 把对象放入到activePool(活动线程池)中
 * 3.调用releaseConnection()释放连接和回收资源
 *
 */
public class DBConnectionPool {


    //初始化空闲线程
    private List<Connection> freePool=new Vector<>();
    //初始化活动线程
    private List<Connection> activePool=new Vector<>();
    //连接参数
    private PoolConfig poolConfig;

    public DBConnectionPool(PoolConfig poolConfig) {
        //获取配置文件信息
        this.poolConfig = poolConfig;
    }

    /**
     * 初始化线程池（初始化空闲线程）
     */
    public void initPool() throws Exception {
        if(poolConfig==null)
        {
            throw new Exception("配置参数为空");
        }
        //获取配置文件参数
        //获取线程池初始化的值
        for (int i=0;i<poolConfig.getInitCon();i++)
        {
              //创建线程连接
            Connection connection = newConnection();
            if(null != connection){
               //添加到空闲数组中
                freePool.add(connection);
           }
        }
    }

    /**
     * 创建线程连接
     */
    private Connection newConnection() {
        //初始化线程,并将初始化后的线程添加到连接池中
        Connection  connection=null;
        try
        {
            if(poolConfig==null)
            {
                throw new Exception("配置参数为空");
            }
              Class.forName(poolConfig.getDriverName());
              connection = DriverManager.getConnection(
                      poolConfig.getUrl(),
                      poolConfig.getUserName(),
                      poolConfig.getPassWord());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
            return connection;

    }


    /**
     * 获取连接对象
     * @return
     */
    public Connection getConnection(){

        return null;
    }

    /**
     * 释放资源，回收连接对象
     */
    public void releaseConnection(){

    }
}
