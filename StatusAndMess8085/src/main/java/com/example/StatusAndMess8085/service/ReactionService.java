package com.example.StatusAndMess8085.service;

import com.example.StatusAndMess8085.entity.ReactionEntity;
import com.example.StatusAndMess8085.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    public void updateReaction(int id, String detail) {
        reactionRepository.updateReaction(id, detail);
    }

    public ReactionEntity addReaction(String detail) {
        ReactionEntity reactionEntity = new ReactionEntity();
        reactionEntity.setDetail(detail);
        return reactionRepository.save(reactionEntity);
    }

    public void deleteReaction(int id) {
        reactionRepository.deleteById(id);
    }
}
