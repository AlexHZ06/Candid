package com.nea.candid.services.database;

import com.nea.candid.data.dbEnties.InteractionsTableEntity;
import com.nea.candid.data.dbEnties.ProfilesTableEntity;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.repositories.InteractionsTableRepo;
import com.nea.candid.repositories.ProfilesTableRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProfilesDbService {

    private final ProfilesTableRepo profilesTableRepo;
    private final InteractionsTableRepo interactionsTableRepo;

    public ProfilesDbService(ProfilesTableRepo profilesTableRepo, InteractionsTableRepo interactionsTableRepo) {
        this.profilesTableRepo = profilesTableRepo;
        this.interactionsTableRepo = interactionsTableRepo;
    }

    public long createProfile(long userId, String profileName, String profileDescription, LocalDateTime createdAt, float mincost, float maxcost, String projectCatagory, double latitude, double longitude){

        long result = profilesTableRepo.insertProfile(userId, profileName, profileDescription, LocalDateTime.now(), mincost, mincost, projectCatagory,latitude,longitude );
        if(result == 0){

            throw new RuntimeException("Failed to insert profile");

        }
        else{

            return result;

        }

    }

    public void setPreferenceVector(long profileid, float[][] vector){

        int result = profilesTableRepo.setPreferenceVector(profileid, vector);
        if(result == 0){

            throw new RuntimeException("Failed to set preference vector");

        }

    }

    public void addInteraction(long photoid, long profileid, String interaction){

        int result =  interactionsTableRepo.addInteraction(photoid, profileid, interaction, LocalDateTime.now());

        if(result == 0){

            throw new RuntimeException("Failed to add interaction");

        }

    }

    public void setDislikesVector(long profileid, float[][] vector){

        int result = profilesTableRepo.setDislikeVector(profileid, vector);
        if(result == 0){

            throw new RuntimeException("Failed to set dislike vector");

        }

    }

    public List<InteractionsTableEntity> getInteractionsByProfile(long profileid){

        return interactionsTableRepo.getInteractionsByProfile(profileid);

    }

    public List<InteractionsTableEntity> getInteractionsByPhoto(long photoid){

        return interactionsTableRepo.getInteractionsByPhoto(photoid);

    }

    public ProfilesTableEntity getProfileById(long profileId){

        return profilesTableRepo.getProfileById(profileId);

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
