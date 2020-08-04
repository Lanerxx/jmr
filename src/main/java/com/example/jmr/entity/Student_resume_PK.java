package com.example.jmr.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
@Data
public class Student_resume_PK implements Serializable {
    @OneToOne
    @JoinColumn(name = "s_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "r_id")
    private Resume resume;
}
