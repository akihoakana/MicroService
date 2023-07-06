package com.example.StatusAndMess8085.DTO;

import com.example.StatusAndMess8085.model.CountTypeReactionModel;
import com.example.StatusAndMess8085.model.DetailUsersReactionModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class StatusDTO {
    private int id;
    private String userPost;
    private String detailPost;
    private Date timeInitial;
    private BigInteger totalReaction;
    private List<CountTypeReactionModel> countTypeReactionModelList;
    private List<DetailUsersReactionModel> detailUsersReactionModelList;
}
