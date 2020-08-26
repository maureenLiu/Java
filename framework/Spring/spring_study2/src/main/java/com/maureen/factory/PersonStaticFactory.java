package com.maureen.factory;

import com.maureen.bean.Person;

/**
 * 静态工厂类
 */
public class PersonStaticFactory {

    public static Person getInstance(String name) {
        Person person = new Person();
        person.setId(10);
        person.setName(name);
        person.setAge(11);
        return person;
    }
}
