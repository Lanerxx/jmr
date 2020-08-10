package com.example.jmr.repository;

import com.example.jmr.entity.Job_match_result;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobMatchResultRepository extends BaseRepository<Job_match_result,Integer>{
    @Query("DELETE  FROM Job_match_result  jmr WHERE jmr.jmr_company.c_id=:cid AND jmr.jmr_job.j_id=:jid ")
    void deleteJob_match_resultsByCompanyAndJob (@Param("cid")int cid, @Param("jid")int jid);
}
