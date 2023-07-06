package com.example.StatusAndMess8085.repository;

import com.example.StatusAndMess8085.entity.ReactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<ReactionEntity, Integer> {
    List<ReactionEntity> findByDetail(String detail);

    @Transactional
    void deleteById(int id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE reaction " +
            "SET detail = :detail WHERE id= :id ")
    void updateReaction(@Param("id") int id
            , @Param("detail") String detail);
}