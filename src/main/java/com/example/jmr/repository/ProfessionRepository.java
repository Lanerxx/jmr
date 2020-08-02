package com.example.jmr.repository;

import com.example.jmr.entity.Profession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessionRepository extends BaseRepository<Profession,Integer>{
    @Query("SELECT pr FROM Profession  pr WHERE pr.p_s_class=:name")
    Optional<Profession> getProfessionByP_s_class (@Param("name")String name);

}
