package com.itdan.spring_demo.aop.trancetion.utils;

import com.itdan.spring_demo.aop.trancetion.annotation.DanTranctional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.Method;

/**
 * 定义事务扫包AOP
 */
@Aspect
@Component
public class DanTranctionAop {

    @Autowired
    private TranctionalUtils tranctionalUtils;

    //环绕通知处理
    @Around("execution(* com.itdan.spring_demo.aop.trancetion.service.*.*.*(..) )")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final DanTranctional annotation = getDanTranctional(proceedingJoinPoint);
        TransactionStatus transactionStatus=null;
        if(null!=annotation){
            //调用开启事物方法
            System.out.println("---开启事务---");
             transactionStatus = tranctionalUtils.begin();
        }
        //3.调用目标代理对象方法
        Object proceed = proceedingJoinPoint.proceed();//如果该方法出现异常不抛出，将不会执行后面的代码
        if(null !=transactionStatus){
        //4.提交事务
            System.out.println("---提交事务---");
            tranctionalUtils.commit(transactionStatus);
        }

    }

    /**
     * 获取当前类上有自定义事务注解的注解对象
     * @param proceedingJoinPoint
     * @return
     */
    private DanTranctional getDanTranctional(ProceedingJoinPoint proceedingJoinPoint) throws NoSuchMethodException {
        //1.获取代理对象的方法
        //（1）获取目标对象的方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        //（2）获取目标对象名
        Class<?> clzName = proceedingJoinPoint.getTarget().getClass();
        // (3) 获取目标对象类型
        Class[] exceptionTypes = ((MethodSignature) proceedingJoinPoint.getSignature()).getExceptionTypes();
        // (4) 获取目标对象方法
        Method method = clzName.getMethod(methodName, exceptionTypes);
        //2.判断目标对象方法上是否存在指定注解
        DanTranctional annotation = method.getDeclaredAnnotation(DanTranctional.class);
        return annotation;
    }

}
