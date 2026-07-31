package com.nea.candid.api.controllers;

import com.nea.candid.data.dto.ProfileBody;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.services.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile/client")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/getprofile")
    public ResponseEntity getProfile(HttpServletRequest request, String profileName){

        ResponseBody responseBody = profileService.getProfileFromTable(Long.parseLong(request.getAttribute("userId").toString()), profileName);
        if(responseBody.isSucsess()){

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);

        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

    }

    @GetMapping("/getprofiles")
    public ResponseEntity getAllProfiles(HttpServletRequest request){

        ResponseBody responseBody = profileService.getAllProfiles(Long.parseLong(request.getAttribute("userId").toString()));
        if(responseBody.isSucsess()){

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);

        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

    }

    @PutMapping("/addprofile")
    public ResponseEntity createProfile(HttpServletRequest request, @RequestBody ProfileBody body){

        try {

            ResponseBody responseBody = profileService.createProfile(

                    Long.parseLong(request.getAttribute("userId").toString()),
                    body.getProfileName(),
                    body.getGetProfileDescription(),
                    body.getMinCost(),
                    body.getMaxCost(),
                    body.getPhotoCategory()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);

        }catch(DataIntegrityViolationException e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseBody.error("This profile name is already used", 402));

        }
        catch (Exception e){

            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseBody.error("Profile not made", 402));

        }

    }

}
