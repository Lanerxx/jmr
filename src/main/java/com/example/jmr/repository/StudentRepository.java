package com.example.jmr.repository;

import com.example.jmr.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends BaseRepository<Student,Integer>{
}
