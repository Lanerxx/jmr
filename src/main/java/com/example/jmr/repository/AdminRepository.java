package com.example.jmr.repository;

import com.example.jmr.entity.Admin;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends BaseRepository<Admin,Integer>{
    Optional<Admin> findByA_name(String name);

}
