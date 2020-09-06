package com.example.jmr.service;

import com.example.jmr.entity.Profession;
import com.example.jmr.repository.ProfessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@Transactional
public class ProfessionService {
    @Autowired
    private ProfessionRepository professtionRepository;
    @Autowired
    private ProfessionService professionService;
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
    public List<Profession> addProfessions(List<Profession> professions){
        return null;
    }
    public void deleteProfession(int pid){
        professtionRepository.deleteById(pid);
    }
    public void deleteAllProfessions(){
        professtionRepository.deleteAll();
    }
    public Profession updateProfession(Profession profession){
        professtionRepository.save(profession);
        return profession;
    }
    public List<Profession> getAllProfessions(){
        return professtionRepository.findAll();
    }
    public Set<String> getProfessionsMClass(){
        Set<String> professionMClasses = new HashSet<>();
        List<Profession> professions = professionService.getAllProfessions();
        professions.forEach(profession -> {
            professionMClasses.add(profession.getP_m_class());
        });

        return professionMClasses;
    }
    public Set<String> getProfessionsSClassByMClass(String mClass){
        Set<String> professionSClasses = new HashSet<>();
        List<Profession> professions = professionService.getProfessionsByMClass(mClass);
        professions.forEach(profession -> {
            professionSClasses.add(profession.getP_s_class());
        });
        return professionSClasses;
    }
    public Profession getProfession(int pid){
        return professtionRepository.findById(pid).orElse(null);
    }
    public Profession getProfessionBySClass(String sClass){
        return professtionRepository.getProfessionByP_s_class(sClass).orElse(null);
    }
    public List<Profession> getProfessionsByMClass(String mClass){
        return professtionRepository.getProfessionByP_m_class(mClass).orElse(new ArrayList<>());
    }
}
