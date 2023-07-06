package com.example.UserSpringJPA8081.repository;

import com.example.UserSpringJPA8081.DTO.NamedDTO;
import com.example.UserSpringJPA8081.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {
    @Query(nativeQuery = true)
    List<NamedDTO> selectUser();

    Optional<UsersEntity> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE users " +
            "SET email = :email,username = :username " +
            ",password = :password,verify_active = :verify_active  WHERE id= :id ")
    void updateUsers(@Param("id") int id
            , @Param("email") String email
            , @Param("username") String username
            , @Param("password") String password
            , @Param("verify_active") boolean verify_active);
}