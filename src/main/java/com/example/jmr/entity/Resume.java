package com.example.jmr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int r_id;

    @NotNull
    @Column(length = 200)
    private String r_m_course;

    @NotNull
    @Column(length = 200)
    private String r_skil;

    @NotNull
    @Column(length = 50)
    private String r_s_certificate;

    @NotNull
    @Column(length = 50)
    private String r_honor;

    @NotNull
    @Column(length = 500)
    private String r_career;

    @NotNull
    @Column(length = 500)
    private String r_p_experience;

    @NotNull
    @Column(length = 200)
    private String r_s_evaluate;

    @NotNull
    @Column(length = 200)
    private String remark;

    @NotNull
    @OneToOne
    @JoinColumn(name = "r_s_id")
    private Student r_student;


}
