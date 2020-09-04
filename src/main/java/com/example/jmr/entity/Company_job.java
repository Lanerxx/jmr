package com.example.jmr.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Company_job {
    private boolean cj_focus;

    @EmbeddedId
    private Company_job_PK company_job_pk;
}
