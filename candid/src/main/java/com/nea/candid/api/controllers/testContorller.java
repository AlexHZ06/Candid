package com.nea.candid.api.controllers;

import com.nea.candid.data.dataObjects.ImageProfileObject;
import com.nea.candid.data.dbEnties.PhotosTableEntity;
import com.nea.candid.services.EmbeddedVectorService;
import com.nea.candid.services.ImageDecoderService;
import com.nea.candid.services.RecommendationService;
import com.nea.candid.services.database.PhotosTableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class testContorller {

    private final ImageDecoderService imageDecoderService;
    private final PhotosTableService photosTableService;
    private final EmbeddedVectorService embeddedVectorService;
    private final RecommendationService  recommendationService;

    public testContorller(ImageDecoderService imageDecoderService, PhotosTableService photosTableService, EmbeddedVectorService embeddedVectorService, RecommendationService recommendationService) {
        this.imageDecoderService = imageDecoderService;
        this.photosTableService = photosTableService;
        this.embeddedVectorService = embeddedVectorService;
        this.recommendationService = recommendationService;
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

    @PostMapping("/public/cosine")
    public void testCoSine(@RequestBody Map<String,Long> body){

        PhotosTableEntity photoOne = photosTableService.getPhotosTableById(body.get("photo1"));
        PhotosTableEntity photoTwo = photosTableService.getPhotosTableById(body.get("photo2"));



        float[] vector1 = photoOne.getGlobalEmbeddedVector();
        float[] vector2 = photoTwo.getGlobalEmbeddedVector();


        embeddedVectorService.calculateRecommendationScore(vector1, vector2);




    }

    @PostMapping("/public/score")
    public void testSimilarity(@RequestBody Map<String,Long> body){

        float score = recommendationService.computeRecommendationScore(body.get("image1"), body.get("image2"));
        System.out.println("score = " + score);

    }


}
