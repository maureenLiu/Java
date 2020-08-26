package com.maureen.factory;

import com.maureen.bean.Person;
import org.springframework.beans.factory.FactoryBean;

/**
 * 此方式是spring创建bean方式的一种补充，用户可以按照需求创建对象，将创建的对象交由Spring IOC容器进行管理。
 * 无论是否是单例，都是在用到的时候才会创建该对象，不用该对象不会创建
 */
public class MyFactoryBean implements FactoryBean<Person> {
    /**
     * 工厂方法，返回需要创建的对象
     *
     * @return
     * @throws Exception
     */
    public Person getObject() throws Exception {
        Person person = new Person();
        person.setId(3);
        person.setName("maureen");
        return person;
    }

    /**
     * 返回创建对象的类型,spring会自动调用该方法返回对象的类型
     *
     * @return
     */
    public Class<?> getObjectType() {
        return Person.class;
    }

    /**
     * 创建的对象是否是单例对象
     *
     * @return
     */
    public boolean isSingleton() {
        return false;
    }
}
