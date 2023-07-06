package com.example.StatusAndMess8085.service;

import com.example.StatusAndMess8085.DTO.StatusDTO;
import com.example.StatusAndMess8085.entity.ReactionStatusEntity;
import com.example.StatusAndMess8085.model.CountTypeReactionModel;
import com.example.StatusAndMess8085.model.DetailUsersReactionModel;
import com.example.StatusAndMess8085.repository.ReactionRepository;
import com.example.StatusAndMess8085.repository.ReactionStatusRepository;
import com.example.StatusAndMess8085.repository.StatusRepository;
import com.example.StatusAndMess8085.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReactionStatusService {

    @Autowired
    private ReactionStatusRepository reactionStatusRepository;
    @Autowired
    private ReactionRepository reactionRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private StatusRepository statusRepository;

    public ReactionStatusEntity addReactionStatus(int userId, int reactionId, int statusId) {
        ReactionStatusEntity reactionStatusEntity = new ReactionStatusEntity();
        if (reactionRepository.findById(reactionId).isPresent()
                && statusRepository.findById(statusId).isPresent()
                && usersRepository.findById(userId).isPresent())
        {
            reactionStatusEntity.setReactionEntity(reactionRepository.findById(reactionId).get());
            reactionStatusEntity.setStatusEntity(statusRepository.findById(statusId).get());
            reactionStatusEntity.setUsersEntity(usersRepository.findById(userId).get());
            reactionStatusEntity.setCreated(new Date(System.currentTimeMillis()));
            return reactionStatusRepository.save(reactionStatusEntity);
        }
        else return null;

    }

    public StatusDTO countLikeByPostId(int id) {
        StatusDTO statusDTO = new StatusDTO();
        List<CountTypeReactionModel> countTypeReactionModelList = new ArrayList<>();
        List<DetailUsersReactionModel> detailUsersReactionModelList = new ArrayList<>();
        List<ReactionStatusEntity> reactionStatusEntity = reactionStatusRepository.findByStatusEntityId(id);
        if (reactionStatusEntity.size()>0 && reactionStatusEntity.get(0).getStatusEntity()!=null
                && reactionRepository.findAll().size()>0){
            statusDTO.setTotalReaction(BigInteger.valueOf(reactionStatusRepository.findByStatusEntityId(id).size()));
            statusDTO.setId(reactionStatusEntity.get(0).getStatusEntity().getId());
            statusDTO.setTimeInitial(reactionStatusEntity.get(0).getStatusEntity().getCreated());
            statusDTO.setDetailPost(reactionStatusEntity.get(0).getStatusEntity().getDetail());
            statusDTO.setUserPost(reactionStatusEntity.get(0).getStatusEntity().getUsersEntity().getEmail());
            System.out.println("reactionRepository.findAll().size() = " + reactionRepository.findAll().size());
            reactionRepository.findAll().forEach(reactionEntity -> {
                System.out.println("reactionEntity.getDetail() = " + reactionEntity.getDetail());
                List<ReactionStatusEntity> reactionStatusEntity1 = reactionStatusEntity.stream()
                        .filter(reactionStatusEntity2 -> reactionStatusEntity2.getReactionEntity().getDetail().equals(reactionEntity.getDetail()))
                        .peek(reactionStatusEntity2 ->
                                System.out.println("reactionStatusEntity2.getUsersEntity().getEmail() = " + reactionStatusEntity2.getUsersEntity().getEmail()))
                        .collect(Collectors.toList());
                if (reactionStatusEntity1.size() > 0 && reactionStatusEntity1.get(0).getReactionEntity()!=null) {
                    CountTypeReactionModel countTypeReactionModel = new CountTypeReactionModel();
                    countTypeReactionModel.setId(reactionStatusEntity1.get(0).getReactionEntity().getId());
                    countTypeReactionModel.setNameReaction(reactionStatusEntity1.get(0).getReactionEntity().getDetail());
                    countTypeReactionModel.setTotalReaction(BigInteger.valueOf(reactionStatusEntity1.size()));
                    reactionStatusEntity1.forEach(reactionStatusEntity2 -> {
                        DetailUsersReactionModel detailUsersReactionModel = new DetailUsersReactionModel();
                        detailUsersReactionModel.setId(reactionStatusEntity2.getUsersEntity().getId());
                        detailUsersReactionModel.setTypeReactionModel(countTypeReactionModel.getNameReaction());
                        detailUsersReactionModel.setTimeReaction(reactionStatusEntity2.getCreated());
                        detailUsersReactionModel.setNameUserReactionModel(reactionStatusEntity2.getUsersEntity().getEmail());
                        detailUsersReactionModelList.add(detailUsersReactionModel);
                    });
                    countTypeReactionModelList.add(countTypeReactionModel);
                }
            });
            statusDTO.setCountTypeReactionModelList(countTypeReactionModelList);
            statusDTO.setDetailUsersReactionModelList(detailUsersReactionModelList);
            return statusDTO;
        }
        else
            return null;

    }
}
