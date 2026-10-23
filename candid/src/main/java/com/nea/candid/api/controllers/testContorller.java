package com.nea.candid.api.controllers;

import com.nea.candid.data.dbEnties.PhotosTableEntity;
import com.nea.candid.data.dbEnties.UsersTableEntity;
import com.nea.candid.services.EmbeddedVectorService;
import com.nea.candid.services.GeoCodingService;
import com.nea.candid.services.ImageDecoderService;
import com.nea.candid.services.RecommendationService;
import com.nea.candid.services.database.PhotosDbService;
import com.nea.candid.services.database.UsersDbService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nea.candid.data.dto.ResponseBody;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class testContorller {

    private final ImageDecoderService imageDecoderService;
    private final PhotosDbService photosTableService;
    private final EmbeddedVectorService embeddedVectorService;
    private final RecommendationService  recommendationService;
    private final GeoCodingService geoCodingService;
    private final UsersDbService usersDbService;

    public testContorller(ImageDecoderService imageDecoderService, PhotosDbService photosTableService, EmbeddedVectorService embeddedVectorService, RecommendationService recommendationService, GeoCodingService geoCodingService, UsersDbService usersDbService) {
        this.imageDecoderService = imageDecoderService;
        this.photosTableService = photosTableService;
        this.embeddedVectorService = embeddedVectorService;
        this.recommendationService = recommendationService;
        this.geoCodingService = geoCodingService;
        this.usersDbService = usersDbService;
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

    @PostMapping("/public/getPhotographers")
    public ResponseBody getPhotographers(){

        List<UsersTableEntity> photos = usersDbService.getAllPhotographers();

        System.out.println("getPhotographers");
        for(int i = 0; i < usersDbService.getAllPhotographers().size(); i++){

            System.out.println(photos.get(i).getUsername());

        }
        return ResponseBody.success(usersDbService.getAllPhotographers(), 955);

    }


}
