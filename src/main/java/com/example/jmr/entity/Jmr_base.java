package com.example.jmr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Jmr_base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jmr_b_id;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int jmr_sex_value;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int jmr_level_value;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int jmr_profession_value;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int jmr_history_value;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int jmr_language_value;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int jmr_range_value;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int jmr_position_value;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int jmr_city_value;

}
