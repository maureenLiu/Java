package com.maureen.test;

import com.maureen.bean.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest2 {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc2.xml");
        Person person = context.getBean("person", Person.class);
        System.out.println(person);
    }
}
