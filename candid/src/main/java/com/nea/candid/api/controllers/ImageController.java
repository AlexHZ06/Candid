package com.nea.candid.api.controllers;

import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.services.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("image")
@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/photographer/uploadimage")
    public ResponseEntity uploadImage(@RequestPart MultipartFile file, @RequestPart String imageName, @RequestPart String userId, @RequestParam String[] tags) {

        long longUserId = Long.parseLong(userId);

        ResponseBody responseBody = imageService.uplaodImage(file, imageName, longUserId, tags);

        if(responseBody.isSucsess()){

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);

        }
        else{

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseBody);

        }

    }

}
