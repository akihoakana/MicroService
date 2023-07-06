package com.example.StatusAndMess8085.DTO;

import com.example.StatusAndMess8085.entity.StatusEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusImageDTO {
    private int id;
    private StatusEntity statusEntity;
    private byte[] bytes;
    private String linkImage;
}
