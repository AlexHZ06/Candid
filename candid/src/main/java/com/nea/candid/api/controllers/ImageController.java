package com.nea.candid.api.controllers;

import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.services.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/image")
@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/photographer/uploadimage")
    public ResponseEntity uploadImage(HttpServletRequest request, @RequestPart MultipartFile file, @RequestPart String imageName, @RequestParam String[] tags) {

        long longUserId = Long.parseLong((String) request.getAttribute("userId"));

        try {
            ResponseBody responseBody = imageService.saveImage(file, imageName, longUserId, tags);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        }
        catch (Exception e) {

            if(e.getMessage().equals("Cannot save to DB")){

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseBody.error("Cannot save to DB", 302));

            }
            else if(e.getMessage().equals("tags id's do not align with number of tags")){

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseBody.error("problem saving tags", 303));

            }
            else if(e.getMessage().equals("Tags junctions do not align with number of tags")){

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseBody.error("issue with saving tags to JT",  304));

            } else if (e.getMessage().equals("Vectors where not saved")) {

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseBody.error("Vectors where not saved", 305));

            } else{

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseBody.error(e.getMessage(), 300));

            }

        }

    }

}
