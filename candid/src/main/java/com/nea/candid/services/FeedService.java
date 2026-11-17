package com.nea.candid.services;

import com.nea.candid.data.dataObjects.FeedImage;
import com.nea.candid.data.dataObjects.FeedRequestObject;
import com.nea.candid.data.dataObjects.PhotoRecoObject;
import com.nea.candid.data.dbEnties.PhotosTableEntity;
import com.nea.candid.data.dbEnties.ProfilesTableEntity;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.repositories.PhotosTableRepo;
import com.nea.candid.repositories.ProfilesTableRepo;
import com.nea.candid.repositories.UsersTableRepo;
import com.nea.candid.services.database.UsersDbService;
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
    private final UsersDbService usersDbService;

    public FeedService(RecommendationService recommendationService, KeyWordService keyWordService, PhotosTableRepo photosTableRepo, ProfilesTableRepo profilesTableRepo, UsersTableRepo usersTableRepo, LocationService locationService, UsersDbService usersDbService) {
        this.recommendationService = recommendationService;
        this.keyWordService = keyWordService;
        this.photosTableRepo = photosTableRepo;
        this.profilesTableRepo = profilesTableRepo;
        this.locationService = locationService;
        this.usersDbService = usersDbService;
    }

    public ResponseBody getFeed(FeedRequestObject body) {

        System.out.println("ran");

        String search = body.getSearch();
        String category = body.getCategory().toLowerCase();
        float minPrice = body.getMinPrice();
        float maxPrice = body.getMaxPrice();
        long userId = body.getUserId();
        float radius = body.getRadius();

        System.out.println(body.getCategory());

        ProfilesTableEntity profile = profilesTableRepo.getProfileById(body.getProfileId());

        List<Long> validPhotographers = new ArrayList<>();

        if(radius != -1){

            validPhotographers =  locationService.calculateDistanceBounds(userId, radius);

        }

        List<String> keyWords = new ArrayList<>();
        int neededWords = 0;

        if(search != ""){

            keyWords = keyWordService.extractKeywords(search);
            neededWords = keyWords.size();

        }


        int totalFeed = 10;
        int totalPhotos = 0;
        List<Long> idList = new ArrayList<>();
        List<List<PhotoRecoObject>> feed = new ArrayList<>();

        while(totalPhotos < totalFeed) {

            System.out.println("loop");
            List<PhotosTableEntity> images = photosTableRepo.getFeedPhotos(keyWords, idList, category, minPrice, maxPrice, validPhotographers, neededWords);
            List<PhotosTableEntity> photos = new ArrayList<>();
            System.out.println(images.size());

            for(int i = 0; i < images.size(); i++){

                idList.add(images.get(i).getPhotoid());
                photos.add(images.get(i));
                totalPhotos += photos.size();
                System.out.println("photo " + images.get(i).getPhotourl());

            }

            if(neededWords > 0){

                neededWords--;

            }
            feed.add(recommendationService.makePhotoRecoObjects(photos, profile.getDislikesvector(), profile.getPreferencevector()));

        }

        return ResponseBody.success(feed, 955);

    }



























}
