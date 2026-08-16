package com.nea.candid.api.controllers;

import com.nea.candid.data.dataObjects.ImageProfileObject;
import com.nea.candid.services.ImageDecoderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/test")
public class testContorller {

    private final ImageDecoderService imageDecoderService;

    public testContorller(ImageDecoderService imageDecoderService) {
        this.imageDecoderService = imageDecoderService;
    }


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


    @PostMapping("/public/bounds")
    public void bounds(MultipartFile file) throws IOException {

        BufferedImage image = ImageIO.read(file.getInputStream());
        imageDecoderService.extractSampleBounds(image, 3);

    }

}
