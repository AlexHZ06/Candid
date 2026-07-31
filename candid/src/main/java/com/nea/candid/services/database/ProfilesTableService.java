package com.nea.candid.services.database;

import com.nea.candid.data.dbEnties.ProfilesTableEntity;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.repositories.ProfilesTableRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProfilesTableService {

    private final ProfilesTableRepo profilesTableRepo;

    public ProfilesTableService(ProfilesTableRepo profilesTableRepo) {
        this.profilesTableRepo = profilesTableRepo;
    }

    public ResponseBody createProfile(long userId, String profileName, String profileDescription, float minCost, float maxCost, String projectCatagory){

        int result = profilesTableRepo.insertProfile(userId, profileName, profileDescription, LocalDateTime.now(), minCost, maxCost, projectCatagory);
        if(result == 0){

            throw new RuntimeException("Failed to insert profile");

        }
        else{

            return ResponseBody.success("Profile inserted", 451);

        }

    }

    public ProfilesTableEntity getProfile(long userId, String profileName){

        ProfilesTableEntity profilesTableEntity = profilesTableRepo.getProfile(userId, profileName);
        if(profilesTableEntity == null){

            throw new RuntimeException("Profile not found");

        }
        else {

            return profilesTableEntity;

        }

    }

    public List<ProfilesTableEntity> getAllProfiles(long userId){

        List<ProfilesTableEntity> profiles = profilesTableRepo.getAllProfiles(userId);
        if(profiles.isEmpty()){

            throw new RuntimeException("No profiles found");

        }
        else{

            return profiles;

        }

    }

}
