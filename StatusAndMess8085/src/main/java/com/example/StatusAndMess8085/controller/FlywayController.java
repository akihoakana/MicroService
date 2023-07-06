package com.example.StatusAndMess8085.controller;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/flyway")
public class FlywayController {

    @Autowired
    private Flyway flyway;

    @PostMapping("/migrate")
    public ResponseEntity<?> migrate() {
        flyway.migrate();
        System.out.println("helloWord I'm migrate");
        return ResponseEntity.ok("helloWord I'm migrate");
    }

    @PostMapping("/base")
    public ResponseEntity<?> baseline() {
        flyway.baseline();
        System.out.println("helloWord I'm baseline");
        return ResponseEntity.ok("helloWord I'm baseline");
    }

    @GetMapping("/info")
    public ResponseEntity<?> info() {
        System.out.println("flyway.info() = " + flyway.info().current());
        System.out.println("flyway.info() = " + flyway.info().pending());
        System.out.println("flyway.info() = " + flyway.info().all());
        System.out.println("flyway.info() = " + flyway.info().toString());
        System.out.println("helloWord I'm info");
        return ResponseEntity.ok(flyway.info());
    }

    @PostMapping("/clean")
    public ResponseEntity<?> clean() {
        flyway.clean();
        System.out.println("helloWord I'm clean");
        return ResponseEntity.ok("helloWord I'm clean");
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate() {
        flyway.validate();
        System.out.println("flyway.validateWithResult() = " + flyway.validateWithResult());
        System.out.println("helloWord I'm validate");
        return ResponseEntity.ok("helloWord I'm validate");
    }

    @PostMapping("/repair")
    public ResponseEntity<?> repair() {
        flyway.repair();
        System.out.println("helloWord I'm repair");
        return ResponseEntity.ok("helloWord I'm repair");
    }
}
