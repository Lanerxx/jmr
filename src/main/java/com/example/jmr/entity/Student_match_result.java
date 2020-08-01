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
//    @Column(name = "smr_c_id")
    private Company smr_c_id;

    @NotNull
    @ManyToOne
//    @Column(name = "smr_s_id")
    private Student smr_s_id;

    @NotNull
    @ManyToOne
//    @Column(name = "smr_r_id")
    private Resume smr_r_id;

    @NotNull
    private int smr_value;
}
