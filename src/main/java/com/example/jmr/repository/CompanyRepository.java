package com.example.jmr.repository;

import com.example.jmr.entity.Company;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends BaseRepository<Company,Integer>{
    @Query("SELECT c FROM Company  c WHERE c.c_telephone=:telephone")
    Optional<Company> getCompanyByC_telephone (@Param("telephone")String telephone);

    @Query("SELECT c FROM Company  c WHERE c.c_name=:name")
    Optional<List<Company>> getCompanyByC_name (@Param("name")String name);

    @Query("SELECT c FROM Company  c WHERE c.c_s_code=:code")
    Optional<Company> getCompanyByC_s_code (@Param("code")String code);

}
