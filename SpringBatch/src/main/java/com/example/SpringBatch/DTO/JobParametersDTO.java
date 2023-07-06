package com.example.SpringBatch.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobParametersDTO {
    private int id;
    private String status;
    private String startTime;
    private String duration;
    private String exitCode;
    private String exitDescription;
}
