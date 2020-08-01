package com.example.jmr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Admin {
    public enum A_TYPE {
        系统管理员,普通管理员
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int a_id;

    @NotNull
    @Column(length = 20)
    private String a_name;

    @NotNull
    @Column(length = 60)
    private String a_password;

    @NotNull
    private A_TYPE a_type;

}
