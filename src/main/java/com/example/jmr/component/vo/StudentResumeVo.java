package com.example.jmr.component.vo;

import com.example.jmr.entity.Resume;
import lombok.Data;

@Data
public class StudentResumeVo {
    private Resume resume;
    private boolean posted;
}
