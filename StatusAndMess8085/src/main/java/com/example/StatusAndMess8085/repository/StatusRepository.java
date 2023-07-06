package com.example.StatusAndMess8085.repository;

import com.example.StatusAndMess8085.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Integer> {
    List<StatusEntity> findByDetail(String detail);

    @Transactional
    void deleteById(int id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE status " +
            "SET detail = :detail WHERE id= :id ")
    void updateStatus(@Param("id") int id
            , @Param("detail") String detail);
}