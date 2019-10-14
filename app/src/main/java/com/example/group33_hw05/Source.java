package com.example.group33_hw05;

import java.io.Serializable;

public class Source implements Serializable {
    String id;
    String name;

    public Source(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Source{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }
}
