package com.nea.candid.services;

import com.nea.candid.data.dataObjects.FeedRequestObject;
import com.nea.candid.data.dataObjects.PhotoRecoObject;
import com.nea.candid.data.dbEnties.PhotosTableEntity;
import com.nea.candid.data.dbEnties.ProfilesTableEntity;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.repositories.PhotosTableRepo;
import com.nea.candid.repositories.ProfilesTableRepo;
import com.nea.candid.repositories.UsersTableRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedService {

    private final RecommendationService recommendationService;
    private final KeyWordService keyWordService;
    private final PhotosTableRepo photosTableRepo;
    private final ProfilesTableRepo profilesTableRepo;
    private final LocationService locationService;

    public FeedService(RecommendationService recommendationService, KeyWordService keyWordService, PhotosTableRepo photosTableRepo, ProfilesTableRepo profilesTableRepo, UsersTableRepo usersTableRepo, LocationService locationService) {
        this.recommendationService = recommendationService;
        this.keyWordService = keyWordService;
        this.photosTableRepo = photosTableRepo;
        this.profilesTableRepo = profilesTableRepo;
        this.locationService = locationService;
    }

    public ResponseBody getFeed(FeedRequestObject body) {

        String search = body.getSearch();
        String category = body.getCategory();
        float minPrice = body.getMinPrice();
        float maxPrice = body.getMaxPrice();
        long userId = body.getUserId();
        float radius = body.getRadius();

        List<Long> validPhotographers =  locationService.calculateDistanceBounds(userId, radius);

        List<String> keyWords = keyWordService.extractNouns(search);

        int totalFeed = 1000;
        List<Long> idList = new ArrayList<>();
        List<PhotosTableEntity> photos = new ArrayList<>();
        int index = 0;

        while(idList.size() <= totalFeed && (idList.size() - index) > 0){

            List<PhotosTableEntity> temp = photosTableRepo.getPhotosByWords(keyWords, idList, category, (idList.size() - index), minPrice, maxPrice, validPhotographers);
            photos.addAll(temp);
            for(int i = 0; i < temp.size(); i++){

                idList.add(temp.get(i).getPhotoid());

            }

            index = index - 1;

        }

        ProfilesTableEntity profile = profilesTableRepo.getProfileById(userId);
        List<PhotoRecoObject> feed = recommendationService.makePhotoRecoObjects(photos, profile.getDislikesvector(), profile.getPreferencevector());
        return ResponseBody.success(feed, 955);

    }



























}
