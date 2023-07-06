package com.example.UserSpringJPA8081.repository;

import com.example.UserSpringJPA8081.entity.TokensEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface TokensRepository extends JpaRepository<TokensEntity, Integer> {
    Optional<TokensEntity> findByIdUsers(int idUsers);

    @Transactional
    @Modifying
    @Query(value = "DELETE from tokens " +
            "WHERE idUsers= ?1",
            nativeQuery = false)
    void deleteByIdUsers(int id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tokens " +
            "SET token = :token ,tokenRefresh = :tokenRefresh WHERE idUsers= :idUsers",
            nativeQuery = false)
    void updateTokenByIdUsers(@Param("idUsers") int idUsers
            , @Param("token") String token
            , @Param("tokenRefresh") String tokenRefresh);
}
