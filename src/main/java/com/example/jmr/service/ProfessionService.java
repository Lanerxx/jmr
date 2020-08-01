package com.example.jmr.service;

import com.example.jmr.entity.Profession;
import com.example.jmr.repository.ProfesstionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class ProfessionService {
    @Autowired
    private ProfesstionRepository professtionRepository;

    /*--------------专业信息（Profession）---------------
    -------检索：管理员
    -------更新：管理员
    -------创建：管理员
    -------删除：管理员
    --------------------------------------------------*/
    public Profession addProfession(Profession profession){
        professtionRepository.save(profession);
        return profession;
    }
}
