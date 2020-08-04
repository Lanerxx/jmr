package com.example.jmr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
public class Student_match_result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int smr_id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "smr_c_id")
    private Company smr_company;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "smr_s_id")
    private Student smr_student;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "smr_r_id")
    private Resume smr_resume;

    @NotNull
    @OneToOne
    @JoinColumn(name = "smr_b_id")
    private Smr_base smr_base;

    @NotNull
    private int smr_value;
}
