package com.itdan.spring_demo.aop.trancetion.test;

import com.itdan.spring_demo.aop.trancetion.annotation.AddAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

public class AnnotationTest {

    @AddAnnotation(userId = 1,userName = "测试人员",arrays = {"1"})
    public void add(){

    }

    public void delete(){

    }

    public static void main(String[] args) throws ClassNotFoundException {
        //通过反射机制去获取上面注解配置的参数
        Class<?> clz = Class.forName("com.itdan.spring_demo.aop.trancetion.test.AnnotationTest");
        //获取当前类（不包含继承对象）的所有方法
        Method[] methods = clz.getDeclaredMethods();

        for (Method method:methods ) {

            System.out.println("method-name:"+method.getName());
            //判断方法上是否存在我们指定的注解
            Annotation annotations = method.getDeclaredAnnotation(AddAnnotation.class);
            if(null==annotations){
                System.out.println("method-name:"+method.getName()+"不存在注解");
               //结束本次循环
               continue;
            }
           //获取注解上的参数
            int userId = ((AddAnnotation) annotations).userId();
            String userName = ((AddAnnotation) annotations).userName();
            String[] arrays = ((AddAnnotation) annotations).arrays();

            System.out.println("userId:"+userId);
            System.out.println("userName:"+userName);
            System.out.println("arrays"+Arrays.toString(arrays));
        }
    }
}
