package com.mqf.study.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 *  将配置文件中配置的每一个属性的值，映射到这个组件中
 *  @ConfigurationProperties : 告诉SpringBoot 将本类中的所有属性和配置文件中相关配置进行绑定
 *           prefix = "person"：配置文件中哪个下面的所有属性进行一一映射
 *  只有这个组件是容器中的组件，才能容器提供的 @ConfigurationProperties 功能
 */
@PropertySource(value = {"classpath:person.properties"}) //引入指定的文件
@Component
@ConfigurationProperties(prefix = "person")
public class Person {

    private String name;
    private Integer age;
    private Boolean boss;
    private Map<String,String> maps;
    private Dog dog;
    private List list;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", boss=" + boss +
                ", map=" + maps +
                ", dog=" + dog +
                ", list=" + list +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getBoss() {
        return boss;
    }

    public void setBoss(Boolean boss) {
        this.boss = boss;
    }

    public Map<String, String> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
