package com.itdan.spring_demo.aop.trancetion.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定事务注解
 *
 * 手写spring事务顺序(思路)
 * 1.定义注解
 * 2.手动封装事务
 * 3.如何扫到指定的包-->定义一个事务扫包AOP，具体定义拦截那些方法
 * 4.拦截到方法后，使用反射机制判断方法上是否存在事物注解,如果存在则开启事物，不存在则不开启事物
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DanTranctional {
}
