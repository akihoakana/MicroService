package com.example.UserSpringJPA8081.repository;

import com.example.UserSpringJPA8081.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, Integer> {
    @Transactional
    void deleteById(int id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE roles " +
            "SET name = :nameRole WHERE id= :oldRoleId",
            nativeQuery = false)
    void updateRolesById(@Param("oldRoleId") int oldRoleId
            , @Param("nameRole") String nameRole);
}
