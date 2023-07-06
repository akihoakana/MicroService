package com.example.StatusAndMess8085.repository;

import com.example.StatusAndMess8085.entity.MessEntity;
import com.example.StatusAndMess8085.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessRepository extends JpaRepository<MessEntity, Integer> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE mess " +
            "SET mess_detail = :detail WHERE id= :id ")
    void updateMess(@Param("id") int id
            , @Param("detail") String detail);

    void deleteByToUser(int toUser);

    void deleteByFromUser(int fromUser);

    List<MessEntity> findByFromUser(Optional<UsersEntity> usersEntity);

    List<MessEntity> findByToUser(Optional<UsersEntity> usersEntity);

    List<MessEntity> findByFromUserAndToUser(Optional<UsersEntity> usersEntity, Optional<UsersEntity> usersEntity1);
}
