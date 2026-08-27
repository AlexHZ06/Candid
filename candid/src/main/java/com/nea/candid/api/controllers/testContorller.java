package com.nea.candid.api.controllers;

import com.nea.candid.data.dbEnties.PhotosTableEntity;
import com.nea.candid.services.EmbeddedVectorService;
import com.nea.candid.services.GeoCodingService;
import com.nea.candid.services.ImageDecoderService;
import com.nea.candid.services.RecommendationService;
import com.nea.candid.services.database.PhotosDbService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class testContorller {

    private final ImageDecoderService imageDecoderService;
    private final PhotosDbService photosTableService;
    private final EmbeddedVectorService embeddedVectorService;
    private final RecommendationService  recommendationService;
    private final GeoCodingService geoCodingService;

    public testContorller(ImageDecoderService imageDecoderService, PhotosDbService photosTableService, EmbeddedVectorService embeddedVectorService, RecommendationService recommendationService, GeoCodingService geoCodingService) {
        this.imageDecoderService = imageDecoderService;
        this.photosTableService = photosTableService;
        this.embeddedVectorService = embeddedVectorService;
        this.recommendationService = recommendationService;
        this.geoCodingService = geoCodingService;
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



    @PostMapping("/public/getcoord")
    public double[] getAddress(@RequestBody Map<String,String> body) throws Exception {

        return geoCodingService.getCoordinates(body.get("address"));

    }


}
