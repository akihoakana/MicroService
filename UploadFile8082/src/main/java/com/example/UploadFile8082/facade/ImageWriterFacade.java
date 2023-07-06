package com.example.UploadFile8082.facade;

import com.example.UploadFile8082.entity.FileDataEntity;
import com.example.UploadFile8082.repository.StorageRepository;
import com.example.UploadFile8082.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImageWriterFacade {
    @Autowired
    private StorageService storageService;
    @Autowired
    private StorageRepository storageRepository;

    @Value("${myHostService.springJPA8081}")
    private String urlHostSpringJPA8081;

    @Value("${myDetailService.springJPA8081.findByEmailId}")
    private String urlDetailFindByEmailId;

    private RestTemplate restTemplate = new RestTemplate();

    public void uploadImageTest(List<MultipartFile> multipartFile, int statusId) {
        System.out.println("uploadImageWithStatusId");
        System.out.println("file = " + multipartFile.size());
        System.out.println("idUser = " + statusId);
        multipartFile.stream()
                .forEach(multipartFile1 -> {
                    System.out.println("multipartFile1.getOriginalFilename() = " + multipartFile1.getOriginalFilename());
                    try {
                        storageService.uploadImageToFileSystem(multipartFile1, statusId);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    public void sendAttachEmailFromFilesDB(String email
            , List<FileDataEntity> fileDataEntities)
            throws IOException, MessagingException {
        storageService.sendWithImageDB(email, fileDataEntities.stream().map(FileDataEntity::getName).collect(Collectors.toList()));
    }

    public void sendEmailWithConfigPathFileByEmail(String email)
            throws IOException, MessagingException {
        storageService.sendEmail(email);
    }

    public void sendAttachEmailByEmail(String email, List<MultipartFile> multipartFile)
            throws IOException, MessagingException {
        storageService.sendAttachEmail(email, multipartFile);
    }

    public void sendEmailWithConfigPathFileById(int id
            , HttpHeaders httpHeaders)
            throws IOException, MessagingException {
        System.out.println(urlHostSpringJPA8081 + urlDetailFindByEmailId);
        String email = restTemplate.exchange(urlHostSpringJPA8081 + urlDetailFindByEmailId
                , HttpMethod.POST
                , new HttpEntity<>(httpHeaders)
                , String.class, id).getBody();
        System.out.println("email = " + email);
        storageService.sendEmail(email);
    }

    public void sendAttachEmailById(int id
            , List<MultipartFile> multipartFile, HttpHeaders httpHeaders)
            throws IOException, MessagingException {
        System.out.println(urlHostSpringJPA8081 + urlDetailFindByEmailId);
        String email = restTemplate.exchange(urlHostSpringJPA8081 + urlDetailFindByEmailId
                , HttpMethod.POST
                , new HttpEntity<>(httpHeaders)
                , String.class, id).getBody();
        System.out.println("email = " + email);
        storageService.sendAttachEmail(email, multipartFile);
        // nhớ bật context-type trong '...' kế bên bulk Edit trong body- form-data

    }

    public void deleteFile(Long id) {
        storageRepository.deleteById(id);
    }

    public void updateFile(Long id, String name) {
        storageRepository.updateFile(id, name);
    }
}
