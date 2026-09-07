package com.nea.candid.services;

import com.nea.candid.data.dataObjects.FeedImage;
import com.nea.candid.data.dataObjects.ImageScore;
import com.nea.candid.data.dbEnties.PhotosTableEntity;
import com.nea.candid.data.dbEnties.ProfilesTableEntity;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.repositories.ProfilesTableRepo;
import com.nea.candid.services.database.PhotosDbService;
import com.nea.candid.services.database.ProfilesDbService;
import com.nea.candid.services.database.UsersDbService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {

    private final PhotosDbService photosTableService;
    private final ProfilesDbService profilesTableService;
    private final UsersDbService usersTableService;
    private final LocationService locationService;

    //this class is also fun it contains the similarity calcs

    public RecommendationService(PhotosDbService photosTableService, ProfilesDbService profilesTableService, UsersDbService usersTableService, LocationService locationService) {
        this.photosTableService = photosTableService;
        this.profilesTableService = profilesTableService;
        this.usersTableService = usersTableService;
        this.locationService = locationService;
    }

    private float calculateEuclideanDistanceSimilarity (float[] vector1, float[] vector2) {

        float inner = 0;

        for (int i = 0; i < vector1.length; i++) {

            inner += (vector1[i] - vector2[i]) * (vector1[i] - vector2[i]);

        }

        float distance = (float) Math.sqrt(inner);
        return (float) ((1) - (distance / (Math.sqrt(vector1.length))));

    }

    public float computeRecommendationScore (long imageId, float[][] preferenceVector) {

        PhotosTableEntity photo = photosTableService.getPhotosTableById(imageId);

        float[][] superVector = createSuperVector(photo.getGlobalEmbeddedVector(), photosTableService.getSectionVectors(photo.getPhotoid()));

        return calculateRecommendationScore(superVector, preferenceVector);

    }

    public float[][] createSuperVector(float[] globalVector, List<float[]> sectionVectors) {

        float[] brightnessMean = new float[17];
        float[] brightnessDeviance = new float[17];
        float[] dynamicRange = new float[17];

        float[] saturationMean = new float[17];
        float[] saturationDeviance = new float[17];
        float[] colourCoverage = new float[17];

        float[] red = new float[17];
        float[] orange = new float[17];
        float[] yellow = new float[17];
        float[] yellowGreen = new float[17];
        float[] green = new float[17];
        float[] greenCyan = new float[17];
        float[] cyan = new float[17];
        float[] cyanBlue = new float[17];
        float[] blue = new float[17];
        float[] bluePurple = new float[17];
        float[] purple = new float[17];
        float[] magenta = new float[17];

        float[] shadows = new float[17];
        float[] highlights = new float[17];

        float[] entropy = new float[17];
        float[] sharpness = new float[17];
        float[] edgeDensity = new float[17];

        brightnessMean[0] = globalVector[0];
        brightnessDeviance[0] = globalVector[1];
        dynamicRange[0] = globalVector[2];

        highlights[0] = globalVector[3];
        shadows[0] = globalVector[4];

        saturationMean[0] = globalVector[5];
        saturationDeviance[0] = globalVector[6];
        colourCoverage[0] = globalVector[7];

        red[0] = globalVector[8];
        orange[0] = globalVector[9];
        yellow[0] = globalVector[10];
        yellowGreen[0] = globalVector[11];
        green[0] = globalVector[12];
        greenCyan[0] = globalVector[13];
        cyan[0] = globalVector[14];
        cyanBlue[0] = globalVector[15];
        blue[0] = globalVector[16];
        bluePurple[0] = globalVector[17];
        purple[0] = globalVector[18];
        magenta[0] = globalVector[19];

        sharpness[0] = globalVector[20];
        edgeDensity[0] = globalVector[21];
        entropy[0] = globalVector[22];

        for(int i = 1; i < sectionVectors.size() + 1; i++) {

            brightnessMean[i] = sectionVectors.get(i - 1)[0];
            brightnessDeviance[i] = sectionVectors.get(i - 1)[1];
            dynamicRange[i] = sectionVectors.get(i - 1)[2];

            highlights[i] = sectionVectors.get(i - 1)[3];
            shadows[i] = sectionVectors.get(i - 1)[4];

            saturationMean[i] = sectionVectors.get(i - 1)[5];
            saturationDeviance[i] = sectionVectors.get(i - 1)[6];
            colourCoverage[i] = sectionVectors.get(i - 1)[7];

            red[i] = sectionVectors.get(i - 1)[8];
            orange[i] = sectionVectors.get(i - 1)[9];
            yellow[i] = sectionVectors.get(i - 1)[10];
            yellowGreen[i] = sectionVectors.get(i - 1)[11];
            green[i] = sectionVectors.get(i - 1)[12];
            greenCyan[i] = sectionVectors.get(i - 1)[13];
            cyan[i] = sectionVectors.get(i - 1)[14];
            cyanBlue[i] = sectionVectors.get(i - 1)[15];
            blue[i] = sectionVectors.get(i - 1)[16];
            bluePurple[i] = sectionVectors.get(i - 1)[17];
            purple[i] = sectionVectors.get(i - 1)[18];
            magenta[i] = sectionVectors.get(i - 1)[19];

            sharpness[i] = sectionVectors.get(i - 1)[20];
            edgeDensity[i] = sectionVectors.get(i - 1)[21];
            entropy[i] = sectionVectors.get(i - 1)[22];

        }

        System.out.println(sectionVectors.get(0).length);

        return new float[][]{
                brightnessMean,
                brightnessDeviance,
                dynamicRange,
                shadows,
                highlights,
                saturationMean,
                saturationDeviance,
                colourCoverage,
                red,
                orange,
                yellow,
                yellowGreen,
                green,
                greenCyan,
                cyan,
                cyanBlue,
                blue,
                bluePurple,
                purple,
                magenta,
                sharpness,
                edgeDensity,
                entropy
        };

    }


    public float calculateRecommendationScore(float[][] superVector1, float[][] superVector2) {

        float[] distances = new float[23];

        for (int i = 0; i < distances.length; i++) {

            distances[i] = calculateEuclideanDistanceSimilarity(
                    superVector1[i],
                    superVector2[i]
            );

        }

//        float brightnessMeanWeight = 1f / 19f;
//        float brightnessDevianceWeight = 1f / 19f;
//        float dynamicRangeWeight = 1f / 19f;
//        float saturationMeanWeight = 1f / 19f;
//        float saturationDevianceWeight = 1f / 19f;
//        float colourSpreadWeight = 1f / 19f;
//        float sharpnessWeight = 1f / 19f;
//        float edgeDensityWeight = 1f / 19f;
//        float entropyWeight = 1f / 19f;
//        float shadowsWeight = 1f / 19f;
//        float highlightsWeight = 1f / 19f;
//        float redWeight = 1f / 19f;
//        float orangeWeight = 1f / 19f;
//        float yellowWeight = 1f / 19f;
//        float greenWeight = 1f / 19f;
//        float cyanWeight = 1f / 19f;
//        float blueWeight = 1f / 19f;
//        float purpleWeight = 1f / 19f;
//        float magentaWeight = 1f / 19f;

//        float brightnessMeanWeight = 2f / 19f;
//        float brightnessDevianceWeight = 2f / 19f;
//        float dynamicRangeWeight = 1f / 19f;
//        float saturationMeanWeight = 1.5f / 19f;
//        float saturationDevianceWeight = 1.5f / 19f;
//        float colourSpreadWeight = 1f / 19f;
//        float sharpnessWeight = 1.5f / 19f;
//        float edgeDensityWeight = 1f / 19f;
//        float entropyWeight = 1.5f / 19f;
//        float shadowsWeight = 1f / 19f;
//        float highlightsWeight = 1f / 19f;
//        float redWeight = 0.5f / 19f;
//        float orangeWeight = 0.5f / 19f;
//        float yellowWeight = 0.5f / 19f;
//        float greenWeight = 0.5f / 19f;
//        float cyanWeight = 0.5f / 19f;
//        float blueWeight = 0.5f / 19f;
//        float purpleWeight = 0.5f / 19f;
//        float magentaWeight = 0.5f / 19f;

//        float brightnessMeanWeight = 2f / 19f;
//        float brightnessDevianceWeight = 1.5f / 19f;
//        float dynamicRangeWeight = 1.5f / 19f;
//        float saturationMeanWeight = 1.5f / 19f;
//        float saturationDevianceWeight = 1.5f / 19f;
//        float colourSpreadWeight = 1f / 19f;
//        float sharpnessWeight = 1.5f / 19f;
//        float edgeDensityWeight = 1.5f / 19f;
//        float entropyWeight = 1f / 19f;
//        float shadowsWeight = 1f / 19f;
//        float highlightsWeight = 1f / 19f;
//        float redWeight = 0.5f / 19f;
//        float orangeWeight = 0.5f / 19f;
//        float yellowWeight = 0.5f / 19f;
//        float greenWeight = 0.5f / 19f;
//        float cyanWeight = 0.5f / 19f;
//        float blueWeight = 0.5f / 19f;
//        float purpleWeight = 0.5f / 19f;
//        float magentaWeight = 0.5f / 19f;

//        float brightnessMeanWeight = 3f / 23f;
//        float brightnessDevianceWeight = 1.5f / 23f;
//        float dynamicRangeWeight = 1.5f / 23f;
//        float saturationMeanWeight = 2f / 23f;
//        float saturationDevianceWeight = 2f / 23f;
//        float colourSpreadWeight = 1f / 23f;
//        float sharpnessWeight = 1f / 23f;
//        float edgeDensityWeight = 1f / 23f;
//        float entropyWeight = 1f / 23f;
//        float shadowsWeight = 2f / 23f;
//        float highlightsWeight = 2f / 23f;
//        float redWeight = ((float) 5 /12) / 23f;
//        float orangeWeight = ((float) 5 /12)  / 23f;
//        float yellowWeight = ((float) 5 /12)  / 23f;
//        float yellowGreenWeight = ((float) 5 /12)  / 23f;
//        float greenWeight = ((float) 5 /12)  / 23f;
//        float greenCyanWeight = ((float) 5 /12)  / 23f;
//        float cyanWeight = ((float) 5 /12)  / 23f;
//        float cyanBlueWeight = ((float) 5 /12)  / 23f;
//        float blueWeight = ((float) 5 /12)  / 23f;
//        float bluePurpleWeight = ((float) 5 /12)  / 23f;
//        float purpleWeight = ((float) 5 /12)  / 23f;
//        float magentaWeight = ((float) 5 /12)  / 23f;

//        float brightnessMeanWeight = 3f / 23f;
//        float brightnessDevianceWeight = 1.5f / 23f;
//        float dynamicRangeWeight = 1.5f / 23f;
//        float saturationMeanWeight = 3f / 23f;
//        float saturationDevianceWeight = 2f / 23f;
//        float colourSpreadWeight = 1f / 23f;
//        float sharpnessWeight = ((float)2/3) / 23f;
//        float edgeDensityWeight = ((float)2/3) / 23f;
//        float entropyWeight = ((float)2/3) / 23f;
//        float shadowsWeight = 2f / 23f;
//        float highlightsWeight = 2f / 23f;
//        float redWeight = ((float) 5 /12) / 23f;
//        float orangeWeight = ((float) 5 /12)  / 23f;
//        float yellowWeight = ((float) 5 /12)  / 23f;
//        float yellowGreenWeight = ((float) 5 /12)  / 23f;
//        float greenWeight = ((float) 5 /12)  / 23f;
//        float greenCyanWeight = ((float) 5 /12)  / 23f;
//        float cyanWeight = ((float) 5 /12)  / 23f;
//        float cyanBlueWeight = ((float) 5 /12)  / 23f;
//        float blueWeight = ((float) 5 /12)  / 23f;
//        float bluePurpleWeight = ((float) 5 /12)  / 23f;
//        float purpleWeight = ((float) 5 /12)  / 23f;
//        float magentaWeight = ((float) 5 /12)  / 23f;

        float brightnessMeanWeight = 2f / 23f;
        float brightnessDevianceWeight = 1.25f / 23f;
        float dynamicRangeWeight = 1.25f / 23f;
        float saturationMeanWeight = 3f / 23f;
        float saturationDevianceWeight = 1f / 23f;
        float colourSpreadWeight = 1f / 23f;
        float sharpnessWeight = 1.5f / 23f;
        float edgeDensityWeight = 1f / 23f;
        float entropyWeight = 1f / 23f;
        float shadowsWeight = 1.5f / 23f;
        float highlightsWeight = 1.5f / 23f;
        float redWeight = ((float) 7 /12) / 23f;
        float orangeWeight = ((float) 7 /12) / 23f;
        float yellowWeight = ((float) 7 /12)  / 23f;
        float yellowGreenWeight =((float) 7 /12) / 23f;
        float greenWeight = ((float) 7 /12)  / 23f;
        float greenCyanWeight = ((float) 7 /12)  / 23f;
        float cyanWeight = ((float) 7 /12)  / 23f;
        float cyanBlueWeight = ((float) 7 /12)  / 23f;
        float blueWeight = ((float) 7 /12)  / 23f;
        float bluePurpleWeight = ((float) 7 /12)  / 23f;
        float purpleWeight = ((float) 7 /12)  / 23f;
        float magentaWeight = ((float) 7 /12)  / 23f;


        return (brightnessMeanWeight * distances[0])
                + (brightnessDevianceWeight * distances[1])
                + (dynamicRangeWeight * distances[2])
                + (shadowsWeight * distances[3])
                + (highlightsWeight * distances[4])
                + (saturationMeanWeight * distances[5])
                + (saturationDevianceWeight * distances[6])
                + (colourSpreadWeight * distances[7])
                + (redWeight * distances[8])
                + (orangeWeight * distances[9])
                + (yellowWeight * distances[10])
                + (yellowGreenWeight * distances[11])
                + (greenWeight * distances[12])
                + (greenCyanWeight * distances[13])
                + (cyanWeight * distances[14])
                + (cyanBlueWeight * distances[15])
                + (blueWeight * distances[16])
                + (bluePurpleWeight * distances[17])
                + (purpleWeight * distances[18])
                + (magentaWeight * distances[19])
                + (sharpnessWeight * distances[20])
                + (edgeDensityWeight * distances[21])
                + (entropyWeight * distances[22]);

    }

    public ResponseBody reCalcPreferenceVector(long profileId, long photoId, String interaction) {

        float interactionWeight = 1;
        switch (interaction) {

            case "like": {
                interactionWeight = 0.5f;
                break;
            }
            case "save": {
                interactionWeight = 0.7f;
                break;
            }
            case "click": {
                interactionWeight = 0.3f;
                break;
            }
            case "dislike": {

                interactionWeight = -0.5f;
                break;
            }
            default: {
                interactionWeight = 0f;
                break;
            }

        }

        PhotosTableEntity photo = photosTableService.getPhotosTableById(photoId);
        ProfilesTableEntity profile = profilesTableService.getProfileById(profileId);

        List<float[]> sectionVectors = photosTableService.getSectionVectors(photoId);
        float[][] superVector = createSuperVector(photo.getGlobalEmbeddedVector(), sectionVectors);
        float[][] preferenceVector = profile.getPreferencevector();
        float[][] dislikeVector = profile.getDislikesvector();

        float[][] newPreferenceVector = profile.getPreferencevector();
        float[][] newDislikeVector = profile.getDislikesvector();

        try{
            if(interactionWeight > 0){

                for (int i = 0; i < superVector.length; i++) {

                    for (int j = 0; j < superVector[i].length; j++) {

                        preferenceVector[i][j] = preferenceVector[i][j] * (1 - interactionWeight);
                        superVector[i][j] = superVector[i][j] * interactionWeight;
                        newPreferenceVector[i][j] = superVector[i][j] + preferenceVector[i][j];

                    }
                }

                profilesTableService.setPreferenceVector(profileId, newPreferenceVector);
                return ResponseBody.success(true, 952);

            }
            else if(interactionWeight < 0){

                for (int i = 0; i < superVector.length; i++) {

                    for (int j = 0; j < superVector[i].length; j++) {

                        dislikeVector[i][j] = dislikeVector[i][j] * (1 - Math.abs(interactionWeight));
                        superVector[i][j] = superVector[i][j] * Math.abs(interactionWeight);
                        newDislikeVector[i][j] = superVector[i][j] + dislikeVector[i][j];

                    }

                }

                profilesTableService.setDislikesVector(profileId, newDislikeVector);
                return ResponseBody.success(true, 952);

            }


        }catch(Exception e){

            return ResponseBody.error("could not save vectors", 903);

        }

        return ResponseBody.error("could not save vectors", 903);

    }

    public List<List<PhotosTableEntity>> getFeed(long profileId, float radius, List<Long> exclude, String category, List<String> tags, int numberOfPhotos){

        ProfilesTableEntity profile = profilesTableService.getProfileById(profileId);
        List<Long> photographers = locationService.calculateDistanceBounds(profileId, radius);
        if(photographers.isEmpty()){

            throw new RuntimeException("No photographers within the radius");

        }

        List<List<PhotosTableEntity>> photos = new ArrayList<>();
        int index = 0;
        boolean exitLoop = false;
        int photoCount = 0;
        while(!exitLoop){

            List<PhotosTableEntity> temp = photosTableService.getFeed(tags, photographers, category, tags.size() - index, exclude);
            for(int i = 0; i < temp.size(); i++){

                exclude.add(temp.get(i).getPhotoid());
                photoCount++;

            }
            photos.add(temp);
            if(tags.size() - index <= 1){

                exitLoop = true;

            }
            if(photoCount >= numberOfPhotos){

                exitLoop = true;

            }

            index++;

        }

        List<List<FeedImage>> feed = new ArrayList<>();
        for(int i = 0; i < photos.size(); i++){

            List<FeedImage> temp = new ArrayList<>();

            for(int j = 0; j < photos.get(i).size(); j++){

                PhotosTableEntity image = photos.get(i).get(j);
                float likeScore = computeRecommendationScore(image.getPhotoid(), profile.getPreferencevector());
                float dislikeScore = computeRecommendationScore(image.getPhotoid(), profile.getDislikesvector());
                if(likeScore > dislikeScore){

                    float finalScore = likeScore - dislikeScore;
                    temp.add(new FeedImage(image, finalScore));

                }
            }

            temp.sort(Comparator.comparingDouble(FeedImage::getLikeScore).reversed());
            feed.add(temp);

        }

        List<List<PhotosTableEntity>> finalFeed =  new ArrayList<>();
        for(int i = 0; i < feed.size(); i++){

            List<PhotosTableEntity> temp = new ArrayList<>();

            for(int j = 0; j < feed.get(i).size(); j++){

                temp.add(feed.get(i).get(j).getPhoto());

            }

            finalFeed.add(temp);

        }

        return finalFeed;

    }

    public ResponseBody getPreferencePhotos(int amount){

        List<PhotosTableEntity> photos = photosTableService.getPreferencePhotos(amount);
        if(photos.isEmpty()){

            return ResponseBody.error("no response from database", 904);

        }
        else {
            return ResponseBody.success(photos, 955);
        }

    }

    public ResponseBody completeProfileVectors(long profileId, long[] likes,  long[] dislikes){

        try{

            for(int i = 0; i < likes.length; i++){

                reCalcPreferenceVector(profileId, likes[i], "like");


            }
            for(int i = 0; i < dislikes.length; i++){

                reCalcPreferenceVector(profileId, dislikes[i], "dislike");

            }

            return ResponseBody.success(true, 952);

        }catch(Exception e){

            System.out.println(e.toString());
            e.printStackTrace();
            return ResponseBody.error("could not save vectors", 903);

        }


    }

}