package com.example.StatusAndMess8085.repository;

import com.example.StatusAndMess8085.entity.ReactionStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ReactionStatusRepository extends JpaRepository<ReactionStatusEntity, Integer> {
    List<ReactionStatusEntity> findByStatusEntityId(int id);

    //    @Transactional
//    void deleteByUser(int id);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE reaction_status " +
            "SET reaction_id = :newReactionId , created= CURRENT_TIMESTAMP WHERE users_id= :users_id and status_id= :status_id ")
    void updateReactionStatusByUserId(@Param("users_id") int userId
            , @Param("newReactionId") int newReactionId
            , @Param("status_id") int statusId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE reaction_status " +
            "SET reaction_id = :reaction_id , created= CURRENT_TIMESTAMP WHERE id= :id ")
    void updateReactionStatus(@Param("id") int id
            , @Param("reaction_id") int reactionId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM reaction_status " +
            "WHERE users_id= :users_id and status_id= :status_id ")
    void deleteReactionStatusByUserId(@Param("users_id") int userId
            , @Param("status_id") int statusId);
}