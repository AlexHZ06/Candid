package com.nea.candid.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class testContorller {

    @PostMapping("/photographer/greet")
    public ResponseEntity photo(){

        return ResponseEntity.ok().body("hi photogrpaher");

    }

    @PostMapping("/client/greet")
    public ResponseEntity client(){

        return ResponseEntity.ok().body("hi client");

    }

    @PostMapping("/greet")
    public ResponseEntity user(){

        return ResponseEntity.ok().body("hi user");

    }

}
