//package com.example.StatusAndMess8085.service;
//
//import com.example.StatusAndMess8085.DTO.MessDTO;
//import com.example.StatusAndMess8085.entity.MessEntity;
//import com.example.StatusAndMess8085.entity.MessReplyEntity;
//import com.example.StatusAndMess8085.repository.MessReplyRepository;
//import com.example.StatusAndMess8085.repository.MessRepository;
//import com.example.StatusAndMess8085.repository.UsersRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//public class MessReplyService {
//
//    @Autowired
//    private MessReplyRepository messReplyRepository;
//    @Autowired
//    private UsersRepository usersRepository;
//    @Autowired
//    private MessRepository messRepository;
//
//    public MessReplyEntity addMessReply(int messId, int userId, String reply){
//        MessReplyEntity messReplyEntity = new MessReplyEntity();
//        messReplyEntity.setReply(reply);
//        messReplyEntity.setMessEntity(messRepository.findById(messId).get());
//        messReplyEntity.setUsersEntity(usersRepository.findById(userId).get());
//        messReplyEntity.setTime(new Date(System.currentTimeMillis()));
//        return messReplyRepository.save(messReplyEntity);
//    }
//
//}
