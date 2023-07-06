package com.example.StatusAndMess8085.controller;

import com.example.StatusAndMess8085.facade.ReactionReaderFacade;
import com.example.StatusAndMess8085.facade.ReactionWriterFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reaction")
public class ReactionController {

    @Autowired
    private ReactionWriterFacade reactionWriterFacade;
    @Autowired
    private ReactionReaderFacade reactionReaderFacade;

    @RequestMapping("/hello")
    public ResponseEntity<?> helloWord() {
        return ResponseEntity.ok("helloWord I'm reaction");
    }

    @PostMapping("/reaction")
    public ResponseEntity<?> getReaction() {
        return ResponseEntity.ok(reactionReaderFacade.getReaction());
    }

    @PostMapping("/deleteReaction/{id}")
    public ResponseEntity<?> deleteReaction(@PathVariable int id) {
        reactionWriterFacade.deleteReaction(id);
        return ResponseEntity.ok("Đã delete");
    }

    @PostMapping("/findReactionById/{id}")
    public ResponseEntity<?> findReactionById(@PathVariable int id) {
        return ResponseEntity.ok(reactionReaderFacade.findReactionById(id));
    }

    @PostMapping("/findReactionByDetail")
    public ResponseEntity<?> findReactionByDetail(@RequestParam(name = "detail") String detail) {
        return ResponseEntity.ok(reactionReaderFacade.findReactionByDetail(detail));
    }

    @PostMapping("/addReaction")
    public ResponseEntity<?> addReaction(@RequestParam(name = "detail") String detail) {
        return ResponseEntity.ok(reactionReaderFacade.addReaction(detail));
    }

    @PostMapping("/updateReaction")
    public ResponseEntity<?> updateReaction(@RequestParam("id") int id
            , @RequestParam("detail") String detail) {
        reactionWriterFacade.updateReaction(id, detail);
        return ResponseEntity.ok("Đã updated");
    }


}
