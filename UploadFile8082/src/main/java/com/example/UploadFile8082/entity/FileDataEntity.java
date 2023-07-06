package com.example.UploadFile8082.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "file_data")
@Getter
@Setter
public class FileDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String filePath;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "mess_id")
    private int messId;
    @Column(name = "status_id")
    private int statusId;
//    @Lob
//    @Column(name = "imagedata",length = 1000)
//    private byte[] imageData;
}