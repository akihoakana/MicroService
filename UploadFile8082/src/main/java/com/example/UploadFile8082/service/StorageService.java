package com.example.UploadFile8082.service;

import com.example.UploadFile8082.entity.FileDataEntity;
import com.example.UploadFile8082.repository.StorageRepository;
import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StorageService {

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private JavaMailSenderImpl mailSender;


    @Value("${myFile.dddddd}")
    private String dddddd;
    @Value("${myFile.Book11}")
    private String Book11;
    @Value("${myFile.uploadImage}")
    private String uploadImage;

    public List<FileDataEntity> uploadManyImageToFileSystem(List<MultipartFile> files) throws IOException {
        List<FileDataEntity> fileDataEntities = new ArrayList<>();
        files.stream().forEach(file -> {
            String filePath = uploadImage + file.getOriginalFilename();
            System.out.println("filePath = " + filePath);
            FileDataEntity fileData = new FileDataEntity();
            fileData.setName(file.getOriginalFilename());
            fileData.setType(file.getContentType());
            fileData.setFilePath(filePath);
            storageRepository.save(fileData);
            try {
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (fileData != null) {
                System.out.println("Success");
                fileDataEntities.add(fileData);
            } else System.out.println("null");
        });
        return fileDataEntities;
    }

    public FileDataEntity uploadImageToFileSystem(MultipartFile files, int statusId) throws IOException {
        String filePath = uploadImage + files.getOriginalFilename();
        System.out.println("filePath = " + filePath);
        FileDataEntity fileData = new FileDataEntity();
        fileData.setName(files.getOriginalFilename());
        fileData.setType(files.getContentType());
        fileData.setFilePath(filePath);
        fileData.setStatusId(statusId);
        storageRepository.save(fileData);
        try {
            files.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileData != null) {
            System.out.println("Success");
        } else System.out.println("null");
        return fileData;
    }

    public List<FileDataEntity> uploadManyImagesWithStatusId(List<MultipartFile> files, int statusId) throws IOException {
        System.out.println("uploadImageWithStatusId");
        List<FileDataEntity> fileDataEntities = new ArrayList<>();
        files.stream().forEach(file -> {
            String filePath = uploadImage + file.getOriginalFilename();
            System.out.println("filePath = " + filePath);
            FileDataEntity fileData = new FileDataEntity();
            fileData.setName(file.getOriginalFilename());
            fileData.setType(file.getContentType());
            fileData.setFilePath(filePath);
            fileData.setStatusId(statusId);
            storageRepository.save(fileData);
            try {
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (fileData != null) {
                System.out.println("Success");
                fileDataEntities.add(fileData);
            } else System.out.println("null");
        });
        return fileDataEntities;
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<FileDataEntity> fileData = storageRepository.findByName(fileName);
        String filePath = fileData.get().getFilePath();
        System.out.println("filePath = " + filePath);
        if (filePath.contains(".png") || filePath.contains(".jpg") || filePath.contains(".jpeg") || filePath.contains(".gif")) {
            byte[] images = Files.readAllBytes(new File(filePath).toPath());
            return images;
        }
        return null;
    }

    public File downloadFileFromFileSystem(String fileName) throws IOException {
        Optional<FileDataEntity> fileData = storageRepository.findByName(fileName);
        return new File(fileData.get().getFilePath());
    }

    public void sendEmail(String email) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        System.out.println(dddddd);
        System.out.println(Book11);
        FileSystemResource file = new FileSystemResource(dddddd);
        FileSystemResource file1 = new FileSystemResource(Book11);
        System.out.println("file.getFilename() = " + file.getFilename());
        System.out.println("file.getFilename() = " + file1.getFilename());
        System.out.println("email = " + email);
        MimeMessageHelper helper = detailMail(email, message);
        helper.addAttachment(file.getFilename(), file);
        helper.addAttachment(file1.getFilename(), file1);
        mailSender.send(message);
    }

    public void sendAttachEmail(String email, List<MultipartFile> multipartFile) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = detailMail(email, message);
        multipartFile.forEach(multipartFile1 -> {
            try {
                helper.addAttachment(multipartFile1.getOriginalFilename(), multipartFile1);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
        mailSender.send(message);
    }

    public void sendWithImageDB(String email, List<String> list) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = detailMail(email, message);
        list.forEach(fileName -> {
            Optional<FileDataEntity> fileData = storageRepository.findByName(fileName);
            FileSystemResource file = new FileSystemResource(fileData.get().getFilePath());
            try {
                helper.addAttachment(fileData.get().getName(), file);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
        mailSender.send(message);
    }

    private MimeMessageHelper detailMail(String email, MimeMessage message) throws MessagingException, UnsupportedEncodingException {
        String toAddress = email;
        String fromAddress = mailSender.getUsername();
        System.out.println("fromAddress = " + fromAddress);
        System.out.println("toAddress = " + toAddress);
        String senderName = "Client-Service-UploadFile8082";
        String subject = "PostMan sent you - Your Image";
        String content = "<h1>Image of you <br></h1>" +
                "You sent image with your email : [[email]]<br>";
        content = content.replace("[[email]]", email);
        MimeMessageHelper helper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);
        return helper;
    }
}
