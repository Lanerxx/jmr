package com.example.jmr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int s_id;

    @NotNull
    @Column(length = 20)
    private String s_name;

    @NotNull
    @Column(length = 72)
    private String s_password;

    @Column(length = 20)
    private String s_id_card;

    @NotNull
    private EnumWarehouse.S_SEX s_sex;

    @NotNull
    @Column(length = 20)
    private String s_birthday;

    @NotNull
    @Column(length = 20)
    private String s_college;

    @NotNull
    private EnumWarehouse.C_LEVEL s_c_level;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "s_profession")
    private Profession s_profession;

    @NotNull
    private EnumWarehouse.E_HISTORY s_e_history;

    @NotNull
    @Column(length = 20)
    private String s_n_province;

    @NotNull
    @Column(length = 20)
    private String s_n_city;

    @NotNull
    private int s_f_language;

    @NotNull
    private EnumWarehouse.S_RANGE s_s_range;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "s_e_position")
    private Position s_e_position;

    @NotNull
    private EnumWarehouse.E_CITY s_e_city;

    @NotNull
    @Column(length = 20)
    private String s_g_time;

    @NotNull
    @Column(length = 20)
    private String s_telephone;

    @NotNull
    @Column(length = 20)
    private String s_email;

    @Column(length = 20)
    private String s_s_attachment;

    @NotNull
    private EnumWarehouse.IF_WORK s_if_work;

    @Column(length = 20)
    private String s_w_city;

    @Column(length = 20)
    private String s_company;

}
