package com.example.jmr.repository;

import com.example.jmr.entity.Company_job;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyJobRepository extends BaseRepository<Company_job,Integer>{
    @Query("SELECT cj FROM Company_job  cj WHERE cj.company_job_pk.job.j_position.p_name=:name")
    Optional<List<Company_job>> getCompany_jobsByPositionName (@Param("name")String name);

    @Query("SELECT cj FROM Company_job  cj WHERE cj.cj_focus=:focus AND cj.company_job_pk.company.c_id=:cid")
    Optional<List<Company_job>> getCompany_jobsByFocusAndCompany (@Param("focus")boolean focus, @Param("cid")int cid);

    @Query("SELECT cj FROM Company_job  cj WHERE cj.company_job_pk.company.c_id=:cid")
    Optional<List<Company_job>> getCompany_jobsByCompany (@Param("cid")int cid);

    @Query("SELECT cj FROM Company_job  cj WHERE cj.company_job_pk.company.c_id=:cid AND cj.company_job_pk.job.j_id=:jid ")
    Optional<Company_job> getCompany_jobByCompanyAndJob (@Param("cid")int cid,@Param("jid")int jid);

    @Modifying
    @Query("DELETE  FROM Company_job  cj WHERE cj.company_job_pk.company.c_id=:cid AND cj.company_job_pk.job.j_id=:jid ")
    void deleteCompany_jobByCompanyAndJob (@Param("cid")int cid,@Param("jid")int jid);
}
