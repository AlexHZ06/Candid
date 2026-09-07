package com.nea.candid.api.controllers;

import com.nea.candid.data.dbEnties.ProfilesTableEntity;
import com.nea.candid.data.dto.ProfileRequest;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.services.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
        if(responseBody.isSuccess()){

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);

        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

    }

    @GetMapping("/getprofiles")
    public ResponseEntity getAllProfiles(HttpServletRequest request){

        ResponseBody responseBody = profileService.getAllProfiles(Long.parseLong(request.getAttribute("userId").toString()));
        if(responseBody.isSuccess()){

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);

        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

    }

    @PostMapping("/addprofile")
    public ResponseEntity createProfile(HttpServletRequest request, @RequestBody ProfileRequest body){

        try {

            ResponseBody responseBody = profileService.createProfile(


                    Long.parseLong(request.getAttribute("userId").toString()),
                    body.getProfilename(),
                    body.getProfiledescription(),
                    LocalDateTime.now(),
                    body.getMincost(),
                    body.getMaxcost(),
                    body.getProjectcatagory(),
                    body.getLatitude(),
                    body.getLongitude(),
                    body.getLikes(),
                    body.getDislikes()

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
