//package com.example.UploadFile8082.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "imagedata")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class ImageDataEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//    private String type;
//    @Lob
//    @Column(name = "imagedata",length = 1000)
//    private byte[] imageData;
//}