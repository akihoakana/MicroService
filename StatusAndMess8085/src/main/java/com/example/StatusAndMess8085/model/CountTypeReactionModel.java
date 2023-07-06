package com.example.StatusAndMess8085.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class CountTypeReactionModel {
    private int id;
    private String nameReaction;
    private BigInteger totalReaction;
}
