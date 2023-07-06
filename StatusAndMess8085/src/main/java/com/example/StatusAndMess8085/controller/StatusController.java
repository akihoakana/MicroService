package com.example.StatusAndMess8085.controller;

//import com.example.StatusAndMess8085.config.RabbitMQReceive;

import com.example.StatusAndMess8085.facade.StatusReadFacade;
import com.example.StatusAndMess8085.facade.StatusWriterFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

//import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/status")
public class StatusController {

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

    @Autowired
    private StatusReadFacade statusReadFacade;
    @Autowired
    private StatusWriterFacade statusWriterFacade;

    @RequestMapping("/hello")
    public ResponseEntity<?> helloWord() {
        return ResponseEntity.ok("helloWord I'm status");
    }

    @PostMapping("/status")
    public ResponseEntity<?> getStatus() {
        return ResponseEntity.ok(statusReadFacade.getStatus());
    }

    @PostMapping("/statusDTOById/{id}")
    public ResponseEntity<?> statusDTOById(@PathVariable int id) {
        return ResponseEntity.ok(statusReadFacade.statusDTOById(id));
    }

    @PostMapping("/reactionStatus")
    public ResponseEntity<?> getReactionStatus() {
        return ResponseEntity.ok(statusReadFacade.getReactionStatus());
    }

    @PostMapping("/reactionStatusById/{id}")
    public ResponseEntity<?> getReactionStatusById(@PathVariable int id) {
        return ResponseEntity.ok(statusReadFacade.getReactionStatusById(id));
    }

    @PostMapping("/deleteStatus/{id}")
    public ResponseEntity<?> deleteStatus(@PathVariable int id) {
        statusWriterFacade.deleteStatus(id);
        return ResponseEntity.ok("Đã delete");
    }

    @PostMapping("/findStatusById/{id}")
    public ResponseEntity<?> findStatusById(@PathVariable int id) {
        return ResponseEntity.ok(statusReadFacade.findStatusById(id));
    }

    @PostMapping("/findStatusByDetail")
    public ResponseEntity<?> findStatusByDetail(@RequestParam(name = "detail") String detail) {
        return ResponseEntity.ok(statusReadFacade.findStatusByDetail(detail));
    }

    @PostMapping("/RestDTO")
    public ResponseEntity<?> RestDTO(@RequestHeader HttpHeaders httpHeaders) {
        return ResponseEntity.ok(statusReadFacade.RestDTO(httpHeaders));
    }

    @PostMapping("/callApi")
    public ResponseEntity<?> callApi(@RequestHeader HttpHeaders httpHeaders) {
        return ResponseEntity.ok(statusReadFacade.callApi(httpHeaders));
    }

    @PostMapping("/callDeleteApi")
    public ResponseEntity<?> callDeleteApi(@RequestHeader HttpHeaders httpHeaders) {
        statusWriterFacade.callDeleteApi(httpHeaders);
        return ResponseEntity.ok("Da callDeleteApi");
    }

    @PostMapping("/uploadClientForm/fileSystem")
    public ResponseEntity<?> uploadClientForm(@RequestPart("image") List<MultipartFile> files
            , @RequestParam("idUser") int idUser
            , @RequestHeader HttpHeaders httpHeaders) throws IOException {
        statusWriterFacade.uploadClientForm(files, idUser, httpHeaders);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/feign/messFindById")
    public ResponseEntity<?> messFindById() {
        return ResponseEntity.ok(statusReadFacade.messFindById(16));
    }

    @PostMapping("/addStatusWithImagesByFeignClient")
    public ResponseEntity<?> addStatusWithImagesByFeignClient(@RequestParam(name = "detail") String detail
            , @RequestParam(name = "idUser") int id
            , @RequestParam("image") List<MultipartFile> files) throws IOException {
        statusWriterFacade.addStatusWithImagesByFeignClient(detail, id, files);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/addStatusWithImages")
    public ResponseEntity<?> addStatusWithImages(@RequestParam(name = "detail") String detail
            , @RequestParam(name = "idUser") int id
            , @RequestParam("image") List<MultipartFile> files, @RequestHeader HttpHeaders httpHeaders) throws IOException {
        statusWriterFacade.addStatusWithImages(detail, id, files, httpHeaders);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/addStatus")
    public ResponseEntity<?> addStatus(
            @RequestParam(name = "detail") String detail
            , @RequestParam(name = "idUser") int idUser) {
        return ResponseEntity.ok(statusReadFacade.addStatus(detail, idUser));
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(
            @RequestParam("id") int id
            , @RequestParam("detail") String detail) {
        statusWriterFacade.updateStatus(id, detail);
        return ResponseEntity.ok("Status đã update");
    }

    @PostMapping("/deleteReactionStatusByUserId")
    public ResponseEntity<?> deleteReactionStatusByUserId(
            @RequestParam("userId") int userId
            , @RequestParam("statusId") int statusId) {
        statusWriterFacade.deleteReactionStatusByUserId(userId, statusId);
        return ResponseEntity.ok("Đã delete");
    }

    @PostMapping("/findReactionStatusById/{id}")
    public ResponseEntity<?> findReactionStatusById(@PathVariable int id) {
        return ResponseEntity.ok(statusReadFacade.findReactionStatusById(id));
    }

    @PostMapping("/addReactionStatus")
    public ResponseEntity<?> addReactionStatus(
            @RequestParam(name = "userId") int userId
            , @RequestParam(name = "reactionId") int reactionId
            , @RequestParam(name = "statusId") int statusId) {
        return ResponseEntity.ok(statusReadFacade.addReactionStatus(userId, reactionId, statusId));
    }

    @PostMapping("/updateReactionStatusByUserId")
    public ResponseEntity<?> updateReactionStatusByUserId(
            @RequestParam("userId") int userId
            , @RequestParam("newReactionId") int newReactionId
            , @RequestParam("statusId") int statusId) {
        statusWriterFacade.updateReactionStatusByUserId(userId, newReactionId, statusId);
        return ResponseEntity.ok("Status đã update");
    }

    @PostMapping("/deleteReactionStatus")
    public ResponseEntity<?> deleteReactionStatus(
            @RequestParam("id") int id) {
        statusWriterFacade.deleteReactionStatus(id);
        return ResponseEntity.ok("Đã delete");
    }

    @PostMapping("/updateReactionStatus")
    public ResponseEntity<?> updateReactionStatus(
            @RequestParam("id") int id
            , @RequestParam("reactionId") int reactionId) {
        statusWriterFacade.updateReactionStatus(id, reactionId);
        return ResponseEntity.ok("Status đã update");
    }
}
