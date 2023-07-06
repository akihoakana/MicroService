package com.example.StatusAndMess8085.facade;

import com.example.StatusAndMess8085.entity.ReactionEntity;
import com.example.StatusAndMess8085.repository.ReactionRepository;
import com.example.StatusAndMess8085.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReactionReaderFacade {

    @Autowired
    private ReactionRepository reactionRepository;
    @Autowired
    private ReactionService reactionService;

    public ReactionEntity addReaction(String detail) {
        return reactionService.addReaction(detail);
    }

    public List<ReactionEntity> findReactionByDetail(String detail) {
        return reactionRepository.findByDetail(detail);
    }

    public List<ReactionEntity> getReaction() {
        return reactionRepository.findAll();
    }

    public Optional<ReactionEntity> findReactionById(int id) {
        return reactionRepository.findById(id);
    }
}
