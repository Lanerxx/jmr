package com.example.jmr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Entity
@Data
@NoArgsConstructor
public class Job {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int j_id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "j_po_id")
    private Position j_position;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private EumnWarehouse.J_SEX j_sex;

    @NotNull
    private EumnWarehouse.E_HISTORY j_e_history;

    @NotNull
    private EumnWarehouse.C_LEVEL j_c_level;

    @NotNull
    private int j_f_language;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "j_pr_id")
    private Profession j_profession;

    @NotNull
    private EumnWarehouse.S_RANGE j_s_range;

    @NotNull
    private EumnWarehouse.E_CITY j_e_city;

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
    private Company j_company;

}
