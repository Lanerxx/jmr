package com.example.jmr.component;

import com.example.jmr.entity.Admin;
import com.example.jmr.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitializingComponent implements InitializingBean {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private AdminService adminService;

    @Override
    public void afterPropertiesSet() throws Exception {
        final String password = "systemAdmin1";
        final String name = "系统管理员1";
        Admin admin = adminService.getAdmin(1);
        if (admin == null) {
            Admin a = new Admin();
            a.setA_name(name);
            String s = encoder.encode(password);
            a.setA_password(encoder.encode(password));
            a.setA_type(Admin.A_TYPE.系统管理员);
            adminService.addAdmin(a);
        }
    }
}
