package com.maureen.bean;

public class Person {
    private int id;
    private String name;
    private Integer age;
    private String gender;

    public Person() {
        System.out.println("Person对象已经被创建");
    }

    public Person(int id, String name, Integer age, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Person(int id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
        System.out.println("age........");
    }

    public Person(int id, String name, String gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        System.out.println("gender..........");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
