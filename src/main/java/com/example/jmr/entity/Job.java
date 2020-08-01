package com.example.jmr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Job {
    public enum J_SEX{
        无要求, 男, 女
    }
    public enum J_E_HISTORY{
        博士, 硕士, 本科, 专科
    }
    public enum J_C_LEVEL{
        _985, _211, 一批本科, 二批本科, 专科, 高职
    }
    public enum J_F_LANGUAGE{
        NO, JN2, JN3, ECET4, ECET6
    }
    public enum J_S_RANGE{
        _4K以下, _4_6K, _6_8K, _8_10K, _10K以上
    }
    public enum J_E_CITY{
        烟台, 其他
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int j_id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "j_po_id")
    private Position j_po_id;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private J_SEX j_sex;

    @NotNull
    private J_E_HISTORY j_e_history;

    @NotNull
    private J_C_LEVEL j_c_level;

    @NotNull
    private int j_f_language;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "j_pr_id")
    private Profession j_pr_id;

    @NotNull
    private J_S_RANGE j_s_range;

    @NotNull
    private J_E_CITY j_e_city;

    @NotNull
    @Column(length = 500)
    private String j_p_require;

    @NotNull
    @Column(length = 200)
    private String j_welfare;

    @NotNull
    @Column(length = 200)
    private String j_remark;

    @NotNull
    @Column(length = 20)
    private String j_expire;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "j_c_id")
    private Company j_c_id;




}
