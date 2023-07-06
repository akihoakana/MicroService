package com.example.StatusAndMess8085.controller;

import com.example.StatusAndMess8085.facade.MessReaderFacade;
import com.example.StatusAndMess8085.facade.MessWriterFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mess")
public class MessController {

    @Autowired
    private MessReaderFacade messReaderFacade;
    @Autowired
    private MessWriterFacade messWriterFacade;


    @RequestMapping("/hello")
    public ResponseEntity<?> helloWord() {
        return ResponseEntity.ok("helloWord I'm mess");
    }

    @PostMapping("/mess")
    public ResponseEntity<?> getRoles() {
        return ResponseEntity.ok(messReaderFacade.getRoles());
    }

    @PostMapping("/messSendByUser/{id}")
    public ResponseEntity<?> messSendByUser(@PathVariable int id) {
        return ResponseEntity.ok(messReaderFacade.messSendByUser(id));
    }

    @PostMapping("/messGetByUser/{id}")
    public ResponseEntity<?> messGetByUser(@PathVariable int id) {
        return ResponseEntity.ok(messReaderFacade.messGetByUser(id));
    }

    @PostMapping("/findByFromUserAndToUser")
    public ResponseEntity<?> findByFromUserAndToUser(
            @RequestParam(name = "fromUserId") int fromUserId,
            @RequestParam(name = "toUserId") int toUserId) {
        return ResponseEntity.ok(messReaderFacade.findByFromUserAndToUser(fromUserId, toUserId));

    }

    @PostMapping("/deleteMess/{id}")
    public ResponseEntity<?> deleteMess(@PathVariable int id) {
        messWriterFacade.deleteMess(id);
        return ResponseEntity.ok("Đã delete" + id);
    }

    @PostMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        return ResponseEntity.ok(messReaderFacade.findById(id));
    }

    @PostMapping("/updateMess")
    public ResponseEntity<?> updateMess(@RequestParam("id") int id
            , @RequestParam("detail") String detail) {
        messWriterFacade.updateMess(id, detail);
        return ResponseEntity.ok("Đã updated");
    }

    @PostMapping("/addMess")
    public ResponseEntity<?> addMess(
            @RequestParam(name = "fromUserId") int fromUserId,
            @RequestParam(name = "toUserId") int toUserId,
            @RequestParam(name = "detail") String detail) {
        return ResponseEntity.ok(messReaderFacade.addMess(fromUserId, toUserId, detail));
    }

    @PostMapping("/fowardMess")
    public ResponseEntity<?> fowardMess(
            @RequestParam(name = "fromUserId") int fromUserId,
            @RequestParam(name = "messId") int messId,
            @RequestParam(name = "fowardUser") int fowardUser) {
        return ResponseEntity.ok(messReaderFacade.fowardMess(fromUserId, messId, fowardUser));
    }

    @PostMapping("/getMessDTOByUsers")
    public ResponseEntity<?> getMessDTOByUsers(
            @RequestParam(name = "fromUserId") int fromUserId,
            @RequestParam(name = "toUserId") int toUserId) {
        return ResponseEntity.ok(messReaderFacade.getMessDTOByUsers(fromUserId, toUserId));
    }
}
