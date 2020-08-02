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

    /*----------------管理员信息（Admin）-----------------
    -------检索：管理员
    -------更新：管理员
    -------创建：系统
    -------删除：系统
    --------------------------------------------------*/
    public Admin addAdmin(Admin admin){
        adminRepository.save(admin);
        return admin;
    }
    public Admin getAdmin(int aid){
        return adminRepository.findById(aid).orElse(null);
    }
    public Admin getAdmin(String name){
        return adminRepository.getAdminByA_name(name).orElse(null);
    }
}
