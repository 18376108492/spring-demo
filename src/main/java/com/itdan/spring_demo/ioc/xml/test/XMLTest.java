package com.itdan.spring_demo.ioc.xml.test;

import com.itdan.spring_demo.ioc.xml.utils.XMLUtil;
import org.dom4j.DocumentException;

/**
 * 解析XML文件测试
 */
public class XMLTest {

    public static void main(String[] args) throws DocumentException {
        XMLUtil xmlUtil=new XMLUtil();
        xmlUtil.xml4Document();
    }
}
