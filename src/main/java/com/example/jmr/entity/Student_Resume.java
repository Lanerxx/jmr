package com.example.jmr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
public class Student_Resume {
    @EmbeddedId
    private Student_resume_PK student_resume_pk;
}
