package com.example.jmr.repository;

import com.example.jmr.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends BaseRepository<Student,Integer>{
    Optional<Student> findByS_name (String name);
}
