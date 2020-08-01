package com.example.jmr.repository;

import com.example.jmr.entity.Job;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends BaseRepository<Job,Integer>{
}
