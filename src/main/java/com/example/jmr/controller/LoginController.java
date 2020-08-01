package com.example.jmr.controller;

import com.example.jmr.component.EncryptComponent;
import com.example.jmr.component.MyToken;
import com.example.jmr.component.vo.UserVo;
import com.example.jmr.entity.Admin;
import com.example.jmr.entity.Company;
import com.example.jmr.entity.Job_director;
import com.example.jmr.entity.Student;
import com.example.jmr.service.AdminService;
import com.example.jmr.service.CompanyService;
import com.example.jmr.service.JobDirectorService;
import com.example.jmr.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
@Slf4j
public class LoginController {
    @Value("${my.systemAdmin}")
    private String roleSystemAdmin;
    @Value("${my.generalAdmin}")
    private String roleGeneralAdmin;
    @Value("${my.company}")
    private String roleCompany;
    @Value("${my.student}")
    private String roleStudent;
    @Value("${my.jobDirector}")
    private String roleJobDirector;

    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private JobDirectorService jobDirectorService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private EncryptComponent encrypt;

    @PostMapping("login")
    public Map login(@RequestBody UserVo login, HttpServletResponse response) {
        log.debug("{}/ {}/ {}/ {}/", login.isAdmin(),login.isCompany(),login.isStudent(),login.isJobDirector());
        String userName = login.getUserName();
        String userPassword = login.getUserPassword();
        int userId = 0;
        MyToken token = new MyToken();
        String roleCode = "";
        if (userName == null || userPassword == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "用户名和密码不能为空。");
        }
        if (login.isAdmin() == true){
            Admin admin = Optional.ofNullable(adminService.getAdmin(userName))
                    .filter(a -> encoder.matches(userPassword, a.getA_password()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名和密码错误"));
            userId = admin.getA_id();
            if (Admin.A_TYPE.系统管理员 == admin.getA_type()){
                roleCode = roleSystemAdmin;
                token.setRole(MyToken.ROLES.SYSTEM_ADMIN);
            }
            else if (Admin.A_TYPE.普通管理员 == admin.getA_type()){
                roleCode = roleGeneralAdmin;
                token.setRole(MyToken.ROLES.GENERAL_ADMIN);
            }
        }
        if (login.isStudent() == true){
            Student student = Optional.ofNullable(studentService.getStudent(userName))
                    .filter(s -> encoder.matches(userPassword, s.getS_password()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名和密码错误"));
            userId = student.getS_id();
            token.setRole(MyToken.ROLES.STUDENT);
            roleCode = roleStudent;
        }
        if (login.isCompany() == true){
            Company company = Optional.ofNullable(companyService.getCompany(userName))
                    .filter(c -> encoder.matches(userPassword, c.getC_password()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名和密码错误"));
            userId = company.getC_id();
            token.setRole(MyToken.ROLES.COMPANY);
            roleCode = roleCompany;
        }
        if (login.isJobDirector() == true){
            Job_director jobDirector = Optional.ofNullable(jobDirectorService.getJobDirector(userName))
                    .filter(jd -> encoder.matches(userPassword, jd.getJd_password()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名和密码错误"));
            userId = jobDirector.getJd_id();
            token.setRole(MyToken.ROLES.JOB_DIRECTOR);
            roleCode = roleJobDirector;
        }
        token.setId(userId);
        String auth = encrypt.encryptToken(token);
        response.setHeader(MyToken.AUTHORIZATION, auth);
        return Map.of(
                "role",roleCode
        );
    }
}
