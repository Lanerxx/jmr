package com.example.jmr;

import com.example.jmr.intercepter.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private CompanyInterceptor companyInterceptor;
    @Autowired
    private StudentInterceptor studentInterceptor;
    @Autowired
    private SystemAdminInterceptor systemAdminInterceptor;
    @Autowired
    private GeneralAdminInterceptor generalAdminInterceptor;
    @Autowired
    private JobDirectorInterceptor jobDirectorInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/login")
                .excludePathPatterns("/api/register/student")
                .excludePathPatterns("/api/register/company");

        registry.addInterceptor(companyInterceptor)
                .addPathPatterns("/api/company**");
        registry.addInterceptor(studentInterceptor)
                .addPathPatterns("/api/student**");
        registry.addInterceptor(jobDirectorInterceptor)
                .addPathPatterns("/api/jobDirector**");
        registry.addInterceptor(systemAdminInterceptor)
                .addPathPatterns("/api/systemAdmin**");
        registry.addInterceptor(generalAdminInterceptor)
                .addPathPatterns("/api/generalAdmin**");

    }

}
