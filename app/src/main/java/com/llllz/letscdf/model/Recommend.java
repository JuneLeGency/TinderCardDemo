package com.llllz.letscdf.model;

public class Recommend {

    String avatorurl;
    String name;
    int age;
    Sex sex;

    public Recommend(String avatorurl, String name, int age, Sex sex) {
        super();
        this.avatorurl = avatorurl;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getAvatorurl() {
        return avatorurl;
    }

    public void setAvatorurl(String avatorurl) {
        this.avatorurl = avatorurl;
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

    public void setAge(int age) {
        this.age = age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getAgeString() {
        return Integer.valueOf(age).toString();
    }
}
