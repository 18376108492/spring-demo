package com.itdan.spring_demo.ioc.annotation.utils;

import com.itdan.spring_demo.ioc.annotation.annotation.DanService;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通过自定义注解初始化IOC容器
 */
public class MyClassPathApplicationContext {

    private  String packageName;//要扫描的包名

    public static ConcurrentHashMap<String,Class<?>> beans=null;//bean容器

    public MyClassPathApplicationContext(String packageName) throws Exception {
        this.packageName = packageName;
        if(StringUtils.isEmpty(packageName)){
            throw new Exception("配置的包名不能为空");
        }
        //初始化容器
        beans=new ConcurrentHashMap<String, Class<?>>();
        initBean();
    }

    /**
     * 通过BeanId获取Bean对象
     * @param beanId
     * @return
     */
    public Object getBean(String beanId) throws Exception {
        if(StringUtils.isEmpty(beanId)){
            throw new Exception("beanId不能为空");
        }
        //通过beanId从容器中获取对象
        Class<?> clz = beans.get(beanId);
        if(null ==clz){
            throw  new Exception("class not find");
        }
        //初始化Bean对象
        return clz.newInstance();
    }

    /**
     *  扫面指定的包初始化自定义注解对象
     */
    public  void initBean() throws Exception {
        //1.通过工具类扫描指定的报名获取到其包下的对象集合
        List<Class<?>> classList = ClassUtil.getClasses(packageName);
        //2.判断对象集合中是否包含我们指定注解
        ConcurrentHashMap<String, Class<?>> beansMap = getBeans(classList);
      if(null ==beansMap || beansMap.isEmpty()){
          throw new Exception("该包下没有配置包含该自定义注解");
      }

    }

    /**
     * 初始化bean容器
     * @param classList
     * @return
     */
    public  ConcurrentHashMap<String,Class<?>> getBeans(List<Class<?>> classList){
        for ( Class<?> clzInfo: classList) {
            //根据class获取类上的指定注解
            DanService annotation = clzInfo.getAnnotation(DanService.class);
            //判断类上是否存在注解
            if(null !=annotation){
                //存在注解,将其存储到beans容器中
                beans.put(toLowerCaseFirstOne(clzInfo.getSimpleName()),clzInfo);
                continue;
            }
        }
        return beans;
    }

    /**
     * 首字母变小写
     * @param text
     * @return
     */
    public static String toLowerCaseFirstOne(String text){
        if (Character.isLowerCase(text.charAt(0)))
            return text;
        else
            return (new StringBuilder()).append(Character.toLowerCase(text.charAt(0))).append(text.substring(1)).toString();
    }
}
