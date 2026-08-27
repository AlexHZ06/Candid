package com.nea.candid.services;

import com.nea.candid.services.database.PhotosDbService;
import com.nea.candid.services.database.ProfilesDbService;
import org.springframework.stereotype.Service;

@Service
public class InteractionsService {

    private final RecommendationService recommendationService;
    private final ProfilesDbService profilesTableService;
    private final PhotosDbService photosTableService;

    public InteractionsService(RecommendationService recommendationService, ProfilesDbService profilesTableService, PhotosDbService photosTableService) {
        this.recommendationService = recommendationService;
        this.profilesTableService = profilesTableService;
        this.photosTableService = photosTableService;

    }

    public void photoLiked(long photoid, long profileId){

        recommendationService.reCalcPreferenceVector(profileId, photoid, "like");
        profilesTableService.addInteraction(photoid, profileId,  "like");


    }

}
