package com.example.jmr.repository;

import com.example.jmr.entity.Job_director;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobDirectorRepository extends BaseRepository<Job_director,Integer>{
    @Query("SELECT jd FROM Job_director  jd WHERE jd.jd_telephone=:telephone")
    Optional<Job_director> getJobDirectorByJd_telephone (@Param("telephone")String telephone);

    @Query("SELECT jd FROM Job_director  jd WHERE jd.jd_name=:name")
    Optional<List<Job_director>> getJobDirectorByJd_name (@Param("name")String name);

}
