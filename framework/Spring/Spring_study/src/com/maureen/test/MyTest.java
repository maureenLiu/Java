package com.maureen.test;

import com.maureen.bean.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 容器中的Person对象是什么时候创建的？
 * 容器中的对象在容器创建完成之前就已经把对象创建好了
 */
public class MyTest {
    public static void main(String[] args) {
        //从IoC中取对象。这个过程中并没有new Person对象，但是已经有Person对象了，即IoC容器完成了对象的创建过程
        /**
         * ApplicationContext：表示IoC容器的入口，想要获取对象的话，必须要创建该类
         *  该类有两个读取配置文件的实现类
         *      ClassPathXmlApplicationContext：表示从classpath中读取数据
         *      FileSystemXmlApplicationContext：表示从当前文件系统读取数据
         */
        ApplicationContext context = new ClassPathXmlApplicationContext("ioc.xml");
        //获取对象的方式1：获取具体的bean实例对象，需要进行强制类型转换
//        Person person =(Person)context.getBean("person");
        //获取对象的方式2：获取对象的时候不需要强制类型转换
        Person person = context.getBean("person", Person.class);
        System.out.println(person); //结果：Person{id=1, name='zhangsan', age=20, gender='男'}
    }
}
