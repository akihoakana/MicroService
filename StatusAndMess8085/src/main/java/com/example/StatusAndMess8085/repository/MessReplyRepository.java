//package com.example.StatusAndMess8085.repository;
//
//import com.example.StatusAndMess8085.entity.MessEntity;
//import com.example.StatusAndMess8085.entity.MessReplyEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import javax.transaction.Transactional;
//
//@Repository
//public interface MessReplyRepository extends JpaRepository<MessReplyEntity,Integer> {
//    @Transactional
//    @Modifying
//    @Query(nativeQuery = true,value = "UPDATE mess_reply " +
//            "SET reply = :reply WHERE id= :id ")
//    void updateMessReply(@Param("id") int id
//            , @Param("reply") String reply);
//}
