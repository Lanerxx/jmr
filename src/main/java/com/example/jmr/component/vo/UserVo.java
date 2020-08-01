package com.example.jmr.component.vo;

import lombok.Data;

import javax.persistence.Column;

@Data
public class UserVo {
    String userName;
    String userPassword;
    boolean isAdmin;
    boolean isStudent;
    boolean isCompany;
    boolean isJobDirector;
}
