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
//        Person bean = context.getBean(Person.class);
//        System.out.println(bean);

        /**
         * 当需要从容器中获取对象的时候，最好要保留无参构造方法，因为底层的实现是反射
         *
         * Class clazz = Person.class;
         * //Object obj = clazz.newInstance(); //默认调用无参构造方法，此方法已经被弃用
         * Object obj = clazz.getDeclaredConstructor().newInstance();
         */

        Person person2 = context.getBean("person2", Person.class);
        System.out.println(person2);

        Person person3 = context.getBean("person3", Person.class);
        System.out.println(person3);

        Person person4 = context.getBean("person4", Person.class);
        System.out.println(person4);

        Person person6 = context.getBean("person6", Person.class);
        System.out.println(person6);

        Person person7 = context.getBean("person7", Person.class);
        System.out.println(person7);

        Person son = context.getBean("son", Person.class);
        System.out.println(son);

        //当ioc.xml中scope为singleton时，比较结果为true；当scope为prototype时，结果为false
        Person person9_1 = context.getBean("person9", Person.class);
        Person person9_2 = context.getBean("person9", Person.class);
        System.out.println(person9_1 == person9_2);

        Person person10 = context.getBean("person10", Person.class);
        System.out.println(person10);

    }
}
