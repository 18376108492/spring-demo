package com.itdan.spring_demo.db_pool.utils;

import com.itdan.spring_demo.db_pool.bean.PoolConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

    //连接池中现存在的连接总数(当前创建的连接数)
    private int count;

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
    private synchronized Connection newConnection() {
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
            count++;
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
        Connection connection=null;
        //判断当前已创建连接数是否小于最大可创建连接数
          if(count<poolConfig.getMaxActiveConn()){
               //当前已创建连接数小于最大值时
               //先判断空闲线程是否有资源
              if(freePool.size()>0){
                     //当空闲线程还存在连接对象时，直接从空闲线程池中获取
                  connection = freePool.remove(0);
              }else {
                   //当空闲线程池中不存在连接对象并且可创建的连接数没有超过最大连接数,可直接创建连接对象
                 connection= newConnection();
              }
              //判断连接对象是否可用
              boolean result = isAvailable(connection);
              if(result){
              //将连接对象存储到活动线程中
              activePool.add(connection);
              }else {
                  //重试机制，重新获取对象（使用递归算法）
                  count--;
                  connection = getConnection();
              }
          }else {
             //大于最大连接数，配值waitTime=1000，表示大于连接数时，进行一秒钟的等待
              try {
                  wait(poolConfig.getWaitTime());
                  connection = getConnection();
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
        return connection;
    }

    /**
     * 判断连接对象是否可用
     */
    private boolean isAvailable(Connection connection) {
        try {
            if(connection==null || connection.isClosed()){
                return false;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return true;
    }

    /**
     * 释放资源，回收连接对象
     */
    public synchronized void releaseConnection(Connection connection){

        //判断连接对象是否可用
        if(isAvailable(connection)){
            //判断空闲线程池是否已满
            if(freePool.size()<poolConfig.getMaxCon()){
               //空闲线程没有满
               //回收连接资源
                freePool.add(connection);
            }else {
               //空闲线程已满
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            activePool.remove(connection);
            count--;
            notifyAll();
        }
    }
}
