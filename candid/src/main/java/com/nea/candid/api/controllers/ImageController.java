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

        try {
            ResponseBody responseBody = imageService.saveImage(file, imageName, longUserId, tags);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        }
        catch (Exception e) {

            if(e.getMessage().equals("Cannot save to DB")){

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseBody("Cannot save to DB", 302, false));

            }
            else if(e.getMessage().equals("tags id's do not align with number of tags")){

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseBody("problem saving tags", 303, false));

            }
            else if(e.getMessage().equals("Tags junctions do not align with number of tags")){

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseBody("issue with saving tags to JT", 304, false));

            }
            else{

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseBody(e.getMessage(), 300, false));

            }

        }

    }

}
