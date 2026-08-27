package com.nea.candid.services;

import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.services.database.ProfilesDbService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final ProfilesDbService profilesTableService;

    public ProfileService(ProfilesDbService profilesTableService) {
        this.profilesTableService = profilesTableService;
    }

    public ResponseBody getProfileFromTable(long userId, String profileName){

        try{

            return ResponseBody.success(profilesTableService.getProfile(userId, profileName), 452);

        }catch(Exception e){

            return ResponseBody.error("Profile was not found", 401);

        }

    }

    public ResponseBody getAllProfiles(long userId){
        try{

            return ResponseBody.success(profilesTableService.getAllProfiles(userId), 452);

        }
        catch(Exception e){

            return ResponseBody.error("Profile was not found", 401);

        }
    }

    @Transactional
    public ResponseBody createProfile(long userId, String profileName, String profileDescription, float minCost,  float maxCost, String projectCategory){

            profilesTableService.createProfile(userId, profileName, profileDescription, minCost, maxCost, projectCategory);
            return ResponseBody.success("profile made", 451);

    }

}
