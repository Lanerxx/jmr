package com.example.jmr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
public class Job_match_result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jmr_id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "jmr_s_id")
    private Student jmr_student;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "jmr_c_id")
    private Company jmr_company;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "jmr_j_id")
    private Job jmr_job;

    @NotNull
    @OneToOne
    @JoinColumn(name = "jmr_b_id")
    private Jmr_base jmr_base;

    @NotNull
    private int jmr_value;
}
