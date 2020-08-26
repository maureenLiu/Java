package com.maureen.factory;

import com.maureen.bean.Person;

/**
 * 实例工厂
 */
public class PersonInstanceFactory {
    public Person getInstance(String name) {
        Person person = new Person();
        person.setId(12);
        person.setName(name);
        person.setAge(13);
        return person;
    }
}
