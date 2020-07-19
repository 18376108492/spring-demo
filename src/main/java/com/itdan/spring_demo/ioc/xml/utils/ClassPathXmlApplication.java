package com.itdan.spring_demo.ioc.xml.utils;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * 解析配置的xml文件工具类
 */
public class ClassPathXmlApplication {
    private String xmlPath;//xml配置文件的路径

    public ClassPathXmlApplication(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    /**
     * 根据beanId获取Bean对象
     * @param beanId
     * @return
     */
    public Object getBean(String beanId) throws Exception {
        if(StringUtils.isEmpty(beanId)){
            throw new Exception("BeanId不能为空");
        }
        //1.解析xml文件
        List<Element> elements = readXml();
        if (null== elements || elements.size()==0){
            throw new Exception("配置文件中未配置相应信息");
        }
      return   getElementInfo(elements,beanId);
    }

    /**
     * 获取标签元素的信息
     * @param elements
     * @param beanId
     * @return
     */
    public Object getElementInfo( List<Element> elements ,String beanId) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        //2.根据id获取到对应的配置bean信息
        for(Element element:elements){
            //获取属性信息
            String xmlBeanId = element.attributeValue("id");
            //判断当前节点是否配置id字段
            if(StringUtils.isEmpty(xmlBeanId)){
                //未配置id字段,中断程序
                continue;
            }
            if(xmlBeanId.equals(beanId)){
                //表示是我们需要的Bean对象
                String className = element.attributeValue("class");
                //3.通过反射机制初始化对象，并存储到IOC容器中
                Object bean = initBean(className);
                return bean;
            }
        }
        return null;
    }

    /**
     * 获取xml配置文件
     */
    private List<Element> readXml() throws Exception {
        //1.解析xml文件信息
        SAXReader saxReader = new SAXReader();
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(xmlPath);
        if(resourceAsStream==null){
            throw new Exception("未读取到配置文件");
        }
        Document read = saxReader.read(resourceAsStream);
        // 2.获取根节点
        Element rootElement = read.getRootElement();
        // 3.获取所有子节点
        List<Element> elements = rootElement.elements();
        return elements;
    }

    /**
     * 通过反射初始化对象
     * @param className
     * @return
     */
    public Object initBean(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> classInfo = Class.forName(className);
        return  classInfo.newInstance();
    }
}
