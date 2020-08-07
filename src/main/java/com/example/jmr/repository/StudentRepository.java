package com.example.jmr.repository;

import com.example.jmr.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends BaseRepository<Student,Integer>{
    @Query("SELECT s FROM Student  s WHERE s.s_telephone=:telephone")
    Optional<Student> getStudentByS_telephone (@Param("telephone")String telephone);

    @Query("SELECT s FROM Student  s WHERE s.s_name=:name")
    Optional<List<Student>> getStudentByS_name (@Param("name")String name);

    @Query("SELECT s FROM Student  s WHERE s.s_college=:college")
    Optional<List<Student>> getStudentByS_college (@Param("college")String college);

}
