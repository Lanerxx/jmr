package com.example.jmr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Job_director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jd_id;

    @NotNull
    @Column(length = 20)
    private String jd_name;

    @NotNull
    @Column(length = 72)
    private String jd_password;

    @NotNull
    @Column(length = 20)
    private String jd_telephone;

    @NotNull
    @Column(length = 20)
    private String jd_email;

}
