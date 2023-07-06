package com.example.SlackSwagger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/slack")
public class SlackController {
    @Autowired
    private SlackConfig slackConfig;

    @GetMapping("/{string}")
    public ResponseEntity<?> sendSlack(@PathVariable String string){
        System.out.println("string = " + string);
        slackConfig.sendMessageToSlack(string);
        ObjectMapper mapper =
                new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return ResponseEntity.ok(string);
    }
}
