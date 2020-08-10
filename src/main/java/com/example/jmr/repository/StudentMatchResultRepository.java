package com.example.jmr.repository;

import com.example.jmr.entity.Student_match_result;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentMatchResultRepository extends BaseRepository<Student_match_result,Integer>{
    @Query("SELECT smr FROM Student_match_result  smr WHERE smr.smr_job.j_id=:jid")
    Optional<List<Student_match_result>> getStudent_match_resultsByJob (@Param("jid")int jid);

    @Query("DELETE  FROM Student_match_result  smr WHERE smr.smr_company.c_id=:cid AND smr.smr_job.j_id=:jid ")
    void deleteStudent_match_resultsByCompanyAndJob (@Param("cid")int cid,@Param("jid")int jid);

    @Query("DELETE  FROM Student_match_result  smr WHERE smr.smr_student.s_id=:sid AND smr.smr_resume.r_id=:rid ")
    void deleteStudent_match_resultsByStudentAndResume (@Param("sid")int sid,@Param("rid")int rid);
}
