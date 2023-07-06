package com.example.StatusAndMess8085.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity(name = "reaction")
public class ReactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String detail;

    @JsonIgnore
    @OneToMany(mappedBy = "reactionEntity", fetch = FetchType.EAGER)
    private Set<ReactionStatusEntity> reactionStatusEntities;
}
