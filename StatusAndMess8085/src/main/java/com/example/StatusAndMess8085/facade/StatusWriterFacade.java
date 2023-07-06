package com.example.StatusAndMess8085.facade;

import com.example.StatusAndMess8085.entity.StatusEntity;
import com.example.StatusAndMess8085.feignclient.UploadFeignClient;
import com.example.StatusAndMess8085.repository.ReactionStatusRepository;
import com.example.StatusAndMess8085.repository.StatusRepository;
import com.example.StatusAndMess8085.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class StatusWriterFacade {
    @Autowired
    UploadFeignClient uploadFeignClient;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private StatusService statusService;
    @Autowired
    private ReactionStatusRepository reactionStatusRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${myHostService.springJPA8081}")
    private String urlHostSpringJPA8081;
    @Value("${myHostService.statusAndMess8085}")
    private String urlHostStatusAndMess8085;
    @Value("${myHostService.uploadFile8082}")
    private String urlHostUploadFile8082;
    @Value("${myDetailService.springJPA8081.RestDTO}")
    private String urlDetailRestDTO;
    @Value("${myDetailService.mess.findById3}")
    private String urlDetailFindById3;
    @Value("${myDetailService.mess.deleteMess4}")
    private String urlDetailDeleteMess4;
    @Value("${myDetailService.image.uploadImageTest}")
    private String urlDetailUploadImageTest;

    public void deleteStatus(int id) {
        statusRepository.deleteById(id);
    }

    public void updateStatus(int id, String detail) {
        statusRepository.updateStatus(id, detail);
    }

    public void updateReactionStatusByUserId(int userId, int newReactionId, int statusId) {
        reactionStatusRepository.updateReactionStatusByUserId(userId, newReactionId, statusId);
    }

    public void updateReactionStatus(int id, int reactionId) {
        reactionStatusRepository.updateReactionStatus(id, reactionId);
    }

    public void deleteReactionStatusByUserId(int userId, int statusId) {
        reactionStatusRepository.deleteReactionStatusByUserId(userId, statusId);
    }

    public void deleteReactionStatus(int id) {
        reactionStatusRepository.deleteById(id);
    }

    public void callDeleteApi(HttpHeaders httpHeaders) {
        System.out.println("urlDetailDeleteMess4 = " + urlHostStatusAndMess8085 + urlDetailDeleteMess4);
        restTemplate.exchange(
                urlHostStatusAndMess8085 + urlDetailDeleteMess4
                , HttpMethod.POST
                , new HttpEntity<>(httpHeaders)
                , String.class);
    }


    public void uploadClientForm(List<MultipartFile> files, int idUser, HttpHeaders httpHeaders) throws IOException {
        System.out.println("idUser = " + idUser);
        files.forEach(file1 -> System.out.println("file1.getOriginalFilename() = " + file1.getOriginalFilename()));
        System.out.println("uploadFeignClient.fileUpload(files, idUser) = "
                + uploadFeignClient.fileUpload(files, idUser));
    }

    public void addStatusWithImagesByFeignClient(String detail, int idUser
            , List<MultipartFile> files) throws IOException {
        statusService.addStatus(detail, idUser);
        System.out.println(urlHostUploadFile8082 + urlDetailUploadImageTest);
        System.out.println("idUser = " + idUser);
        files.forEach(file1 -> System.out.println("file1.getOriginalFilename() = " + file1.getOriginalFilename()));
        System.out.println("uploadFeignClient.fileUpload(files, idUser) = "
                + uploadFeignClient.fileUpload(files, idUser));
    }

    public void addStatusWithImages(String detail, int id
            , List<MultipartFile> files, HttpHeaders httpHeaders) throws IOException {
        statusService.addStatus(detail, id);
        System.out.println(urlHostUploadFile8082 + urlDetailUploadImageTest);
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("idUser", id);
        List<String> tempFileNames = new ArrayList<>();
        FileOutputStream fo;
        for (MultipartFile file : files) {
            System.out.println("tempFileName = " + file.getOriginalFilename());
            tempFileNames.add(file.getOriginalFilename());
            fo = new FileOutputStream(file.getOriginalFilename());
            fo.write(file.getBytes());
            fo.close();
            map.add("image", new FileSystemResource(file.getOriginalFilename()));
        }
        System.out.println("map.size() = " + map.size());
        System.out.println("map.get(\"image\").size() = " + map.get("image").size());
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, httpHeaders);
        String response = restTemplate.postForObject(urlHostUploadFile8082 + urlDetailUploadImageTest, requestEntity, String.class);
        System.out.println("response = " + response);

        for (String fileName : tempFileNames) {
            File f = new File(fileName);
            f.delete();
        }
    }

}
