package com.example.advancedskill;

import java.io.Serializable;

public class person implements Serializable {
    private String name;
    private int age;

    public person(String name, int age) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
