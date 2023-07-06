package com.example.SpringBatch.repository;

import com.example.SpringBatch.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity,Integer> {
}
