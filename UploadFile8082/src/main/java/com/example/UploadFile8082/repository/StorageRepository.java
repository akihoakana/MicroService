package com.example.UploadFile8082.repository;

import com.example.UploadFile8082.entity.FileDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface StorageRepository extends JpaRepository<FileDataEntity, Long> {
    @Transactional
    Optional<FileDataEntity> findByName(String name);

    @Transactional
    @Modifying
    @Query(value = "UPDATE file_data " +
            "SET name = :name  WHERE id= :id",
            nativeQuery = true)
    void updateFile(@Param("id") Long idUsers
            , @Param("name") String name);
}
