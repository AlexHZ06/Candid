package com.nea.candid.services;

import com.nea.candid.data.dbEnties.PhotosTableEntity;
import com.nea.candid.data.dbEnties.ProfilesTableEntity;
import com.nea.candid.services.database.PhotosTableService;
import com.nea.candid.services.database.ProfilesTableService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    private final PhotosTableService photosTableService;
    private final ProfilesTableService profilesTableService;

    public RecommendationService(PhotosTableService photosTableService, ProfilesTableService profilesTableService) {
        this.photosTableService = photosTableService;
        this.profilesTableService = profilesTableService;
    }

    private float calculateEuclideanDistanceSimilarity (float[] vector1, float[] vector2) {

        float inner = 0;

        for (int i = 0; i < vector1.length; i++) {

            inner += (vector1[i] - vector2[i]) * (vector1[i] - vector2[i]);

        }

        float distance = (float) Math.sqrt(inner);
        return (float) ((1) - (distance / (Math.sqrt(vector1.length))));

    }

    public float computeRecommendationScore (long imageId1, long imageId2) {

        PhotosTableEntity photo1 = photosTableService.getPhotosTableById(imageId1);
        PhotosTableEntity photo2 = photosTableService.getPhotosTableById(imageId2);

        List<float[]> photo1Vectors = photosTableService.getSectionVectors(imageId1);
        List<float[]> photo2Vectors = photosTableService.getSectionVectors(imageId2);

        float[][] superVector1 = createSuperVector(photo1.getGlobalEmbeddedVector(), photo1Vectors);
        float[][] superVector2 = createSuperVector(photo2.getGlobalEmbeddedVector(), photo2Vectors);

        return calculateRecommendationScore(superVector1, superVector2);

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
//
//        float saturationMeanWeight = 2f / 23f;
//        float saturationDevianceWeight = 2f / 23f;
//        float colourSpreadWeight = 1f / 23f;
//
//        float sharpnessWeight = 1f / 23f;
//        float edgeDensityWeight = 1f / 23f;
//        float entropyWeight = 1f / 23f;
//
//        float shadowsWeight = 2f / 23f;
//        float highlightsWeight = 2f / 23f;
//
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
//
//        float saturationMeanWeight = 3f / 23f;
//        float saturationDevianceWeight = 2f / 23f;
//        float colourSpreadWeight = 1f / 23f;
//
//        float sharpnessWeight = ((float)2/3) / 23f;
//        float edgeDensityWeight = ((float)2/3) / 23f;
//        float entropyWeight = ((float)2/3) / 23f;
//
//        float shadowsWeight = 2f / 23f;
//        float highlightsWeight = 2f / 23f;
//
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

    public void reCalcPreferenceVector(long profileId, long photoId, String interaction) {

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
        float[][] newPreferenceVector = profile.getPreferencevector();

        float firstPart = 0;
        for (int i = 0; i < sectionVectors.size(); i++) {

            for (int j = 0; j < sectionVectors.get(i).length; j++) {

                preferenceVector[i][j] = preferenceVector[i][j] * (1 - interactionWeight);
                superVector[i][j] = superVector[i][j] * interactionWeight;
                newPreferenceVector[i][j] = superVector[i][j] + preferenceVector[i][j];

            }

        }

        profilesTableService.setPreferenceVector(profileId, newPreferenceVector);

    }

    public void removeInteractionFromPreference(long profileId, long photoId, long interactionId) {



    }

}