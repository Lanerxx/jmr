package com.example.jmr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Student {
    public enum S_SEX {
        男, 女
    }
    public enum S_C_LEVEL{
        _985, _211, 一批本科, 二批本科, 专科, 高职
    }
    public enum S_E_HISTORY{
        博士, 硕士, 本科, 专科
    }
    public enum S_S_RANGE{
        _4K以下, _4_6K, _6_8K, _8K以上,
    }
    public enum S_E_CITY{
        烟台, 其他
    }
    public enum S_IF_WORK{
        未就业, 已就业
    }
    public enum S_F_LANGUAGE{
        NO, JN2, JN3, ECET4, ECET6
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int s_id;

    @NotNull
    @Column(length = 20)
    private String s_name;

    @NotNull
    @Column(length = 20)
    private String s_password;

    @Column(length = 20)
    private String s_id_card;

    @NotNull
    private S_SEX s_sex;

    @NotNull
    @Column(length = 20)
    private String s_birthday;

    @NotNull
    @Column(length = 20)
    private String s_college;

    @NotNull
    private S_C_LEVEL s_c_level;

    @ManyToOne
    @NotNull
//    @Column(name = "s_profession")
    private Profession s_profession;

    @NotNull
    private S_E_HISTORY s_e_history;

    @NotNull
    @Column(length = 20)
    private String s_n_province;

    @NotNull
    @Column(length = 20)
    private String s_n_city;

    @NotNull
    private int s_f_language;

    @NotNull
    private S_S_RANGE s_s_range;

    @ManyToOne
    @NotNull
//    @Column(name = "s_e_position")
    private Position s_e_position;

    @NotNull
    private S_E_CITY s_e_city;

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
    private S_IF_WORK s_if_work;

    @Column(length = 20)
    private String s_w_city;

    @Column(length = 20)
    private String s_company;

}
