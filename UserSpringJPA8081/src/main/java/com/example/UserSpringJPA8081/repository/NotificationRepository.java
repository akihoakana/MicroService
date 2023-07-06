package com.example.UserSpringJPA8081.repository;

import com.example.UserSpringJPA8081.entity.NotificationEntity;
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
public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE notification " +
            "SET detail = :detail WHERE id= :id",
            nativeQuery = false)
    void updateNotificationById(@Param("id") int id
            , @Param("detail") String detail);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM notification" +
            " WHERE user_id= :id",
            nativeQuery = false)
    void deleteNotificationByUserId(@Param("id") int id);

    List<NotificationEntity> findByUsersEntity(Optional<UsersEntity> usersEntity);
//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE notification " +
//            "SET detail = :detail WHERE user_Id= :userId",
//            nativeQuery = true)
//    void updateNotificationByIdUser(@Param("userId") int userId
//            , @Param("detail") String detail);
}
