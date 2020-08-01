package com.example.jmr.repository;

import com.example.jmr.entity.Company;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends BaseRepository<Company,Integer>{
    Optional<Company> findByC_name (String name);
}
