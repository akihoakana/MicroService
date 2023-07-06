package com.example.SpringBatch.repository;

import com.example.SpringBatch.DTO.UserEntityBatch;
import com.example.SpringBatch.entity.UsersEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity,Integer> {
    @Modifying
    @Transactional
    @Query(countQuery = "1",value = "update users  set fullname = :fullname where users.id=:userId", nativeQuery = true)
    int updateFullNameByUserId(@Param("fullname") String fullName, @Param("userId") int userId);

@Modifying
    @Transactional
    @Query(countQuery = "1",value = "update users  set role_id = :roleId where users.id=:userId", nativeQuery = true)
    int updateRoleIdByUserId(@Param("roleId") int roleId, @Param("userId") int userId);
    @Transactional
    @Query(nativeQuery = true)
//    UserEntityBatch selectUserDTOBatch();
    Page<UserEntityBatch> selectUserDTOBatch(Pageable pageable);

    Page<UsersEntity> findById(int id, Pageable pageable);

}