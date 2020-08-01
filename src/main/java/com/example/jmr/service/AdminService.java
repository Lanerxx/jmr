package com.example.jmr.service;

import com.example.jmr.entity.Admin;
import com.example.jmr.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Admin addAdmin(Admin admin){
        adminRepository.save(admin);
        return admin;
    }

    public Admin getAdmin(int aid){
        return adminRepository.findById(aid).orElse(null);
    }
}
