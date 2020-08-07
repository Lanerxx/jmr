package com.example.jmr.repository;

import com.example.jmr.entity.Student_Resume;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentResumeRepository extends BaseRepository<Student_Resume,Integer>{
    @Query("SELECT sr FROM Student_Resume  sr WHERE sr.student_resume_pk.student.s_id=:sid")
    Optional<List<Student_Resume>> getStudentResumesByStudent (@Param("sid")int sid);

}
