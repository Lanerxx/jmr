package com.example.jmr.repository;

import com.example.jmr.entity.Position;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PositionRepository extends BaseRepository<Position,Integer>{
    @Query("SELECT po FROM Position  po WHERE po.p_name=:name")
    Optional<Position> getPositionByP_name (@Param("name")String name);

}
