package com.example.jmr.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
@Data
public class Student_resume_PK implements Serializable {
    @OneToOne
//    @Column(name = "s_id")
    private Student s_id;

    @OneToOne
//    @Column(name = "r_id")
    private Resume r_id;
}
