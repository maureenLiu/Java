package com.maureen.test;

import com.maureen.bean.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc.xml");
        /**
         * Spring对象的获取及属性赋值方式
         * 1、根据bean标签的id来获取对象
         */
        Person person = context.getBean("person", Person.class);
        System.out.println(person);

        /**
         * 2、根据bean的类型来获取对象
         * 注意：当通过类型进行获取的时候，如果存在两个相同类型对象，将无法完成获取工作
         */
        Person bean = context.getBean(Person.class);
        System.out.println(bean);
    }
}
