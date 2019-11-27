package com.rsc.controller.admin;


import lombok.Data;

@Data
public class Echarts {
    private String name;
    // private Integer value;
    private Object value;

    public Echarts(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Echarts() {
    }
}
