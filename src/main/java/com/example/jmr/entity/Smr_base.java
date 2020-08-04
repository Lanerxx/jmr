package com.example.jmr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
public class Smr_base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int smr_b_id;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int smr_sex_value;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int smr_level_value;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int smr_profession_value;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int smr_history_value;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int smr_language_value;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int smr_range_value;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int smr_position_value;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int smr_city_value;


}
