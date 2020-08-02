package com.example.jmr.component.vo;

import lombok.Data;

import javax.persistence.Column;

@Data
public class UserVo {
    String userPhoneNumber;
    String userPassword;
    String userName;
    boolean admin;
    boolean student;
    boolean company;
    boolean jobDirector;
}
