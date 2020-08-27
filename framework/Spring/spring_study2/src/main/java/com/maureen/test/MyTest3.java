package com.maureen.test;

import com.maureen.bean.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * SpEL表达式的使用
 */
public class MyTest3 {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc3.xml");
        Person person = context.getBean("person", Person.class);
        System.out.println(person);
    }
}
