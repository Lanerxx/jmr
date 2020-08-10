package com.example.jmr.component.vo;

import com.example.jmr.entity.Job;
import lombok.Data;

@Data
public class CompanyJobVo {
    private Job job;
    private boolean posted;
    private int cj_focus;
}
