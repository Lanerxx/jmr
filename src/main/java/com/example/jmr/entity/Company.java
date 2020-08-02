package com.example.jmr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int c_id;

    @NotNull
    @Column(length = 20)
    private String c_name;

    @NotNull
    @Column(length = 72)
    private String c_password;

    @NotNull
    @Column(length = 20)
    private String c_s_code;

    @NotNull
    @Column(length = 500)
    private String c_description;

    @NotNull
    @Column(length = 20)
    private String c_contact;

    @NotNull
    @Column(length = 20)
    private String c_telephone;

    @NotNull
    @Column(length = 20)
    private String c_email;

}
