package com.example.UserSpringJPA8081.repository;

import com.example.UserSpringJPA8081.entity.UsersRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UsersRolesRepository extends JpaRepository<UsersRolesEntity, Integer> {
    List<UsersRolesEntity> findByUsersId(int userId);

    List<UsersRolesEntity> findByRolesId(int roleId);

    @Transactional
    void deleteByUsersIdAndRolesId(int userId, int roleId);

    @Transactional
    void deleteByUsersId(int userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users_roles " +
            "SET rolesId = :newRoleId WHERE usersId= :userId and rolesId= :oldRoleId")
    void updateRolesByUserId(@Param("userId") int userId
            , @Param("oldRoleId") int oldRoleId
            , @Param("newRoleId") int newRoleId);
//    @Query(value = "UPDATE users_roles " +
//            "SET rolesId = :newRoleId WHERE usersId= :userId and rolesId= :oldRoleId")
//    @Query(value = "INSERT INTO users_roles(users_id,roles_id) " +
//            " VALUES (:userId,:roleId)",nativeQuery = true)
//    UsersRolesEntity addUserRole(@Param("userId") int userId
//            , @Param("roleId") int roleId);
}