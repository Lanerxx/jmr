package com.example.jmr.repository;

import com.example.jmr.entity.Job_match_result;
import com.example.jmr.entity.Student_match_result;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobMatchResultRepository extends BaseRepository<Job_match_result,Integer>{
    @Modifying
    @Query("DELETE  FROM Job_match_result  jmr WHERE jmr.jmr_company.c_id=:cid AND jmr.jmr_job.j_id=:jid ")
    void deleteJob_match_resultsByCompanyAndJob (@Param("cid")int cid, @Param("jid")int jid);

    @Query("SELECT jmr FROM Job_match_result  jmr WHERE jmr.jmr_student.s_id=:sid")
    Optional<List<Job_match_result>> getJob_match_resultsByStudent (@Param("sid")int sid);

}
