package com.example.jmr.repository;

import com.example.jmr.entity.Job_director;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobDirectorRepository extends BaseRepository<Job_director,Integer>{
    Optional<Job_director> findByJd_name(String name);
}
