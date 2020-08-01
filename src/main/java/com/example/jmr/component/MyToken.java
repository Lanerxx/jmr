package com.example.jmr.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyToken {
    public enum ROLES{
        SYSTEM_ADMIN, GENERAL_ADMIN, STUDENT, COMPANY, JOB_DIRECTOR
    }
    //Avoid hard coding
    public static final String AUTHORIZATION = "authorization";
    public static final String ID = "id";
    public static final String ROLE = "role";

    private int id;
    private ROLES role;
}

