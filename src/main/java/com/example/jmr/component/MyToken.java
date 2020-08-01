package com.example.jmr.component;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyToken {
    public enum ROLE{
        系统管理员, 普通管理员, 学生用户, 企业用户, 就业专员
    }
    //Avoid hard coding
    public static final String AUTHORIZATION = "authorization";
    public static final String ID = "id";
    public static final String ROLE = "role";

    private int id;
    private ROLE role;
}

