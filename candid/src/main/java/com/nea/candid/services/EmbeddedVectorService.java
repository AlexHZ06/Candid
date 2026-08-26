package com.nea.candid.services;

import com.nea.candid.data.dataObjects.EmbeddedVector;
import com.nea.candid.data.dataObjects.ImageProfileObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EmbeddedVectorService {

    public void addVector(ImageProfileObject imageProfileObject, float[] brightness, float[] saturation, float[] hues, float[] shadowHighlight, float[] texture){

        imageProfileObject.getEmbededVectors().add(
                new EmbeddedVector(brightness[0], brightness[1], brightness[2], shadowHighlight[0], shadowHighlight[1], saturation[0], saturation[1], saturation[2], hues, texture[0], texture[1], texture[2])
        );

    }

    public void setGlobalVector(ImageProfileObject imageProfileObject,float[] brightness, float[] saturation, float[] hues, float[] shadowHighlight, float[] texture){

        imageProfileObject.setGlobalEmbeddedVector(
                new EmbeddedVector(brightness[0], brightness[1], brightness[2], shadowHighlight[0], shadowHighlight[1], saturation[0], saturation[1], saturation[2], hues, texture[0], texture[1], texture[2])
        );

    }

    public void generateSectionVectors(ImageProfileObject imageProfileObject) {

        for (int i = 0; i < imageProfileObject.getEmbeddedVectors().size(); i++) {

            EmbeddedVector e = imageProfileObject.getEmbeddedVectors().get(i);

            float[] vector = new float[23];

            vector[0] = e.getBrightnessMean();
            vector[1] = e.getBrightnessDev();
            vector[2] = e.getDynamicRange();

            vector[3] = e.getHighlights();
            vector[4] = e.getShadows();

            vector[5] = e.getSaturationMean();
            vector[6] = e.getSaturationDev();
            vector[7] = e.getColourCoverage();

            float[] colours = e.getColours();

            vector[8] = colours[0];
            vector[9] = colours[1];
            vector[10] = colours[2];
            vector[11] = colours[3];
            vector[12] = colours[4];
            vector[13] = colours[5];
            vector[14] = colours[6];
            vector[15] = colours[7];
            vector[16] = colours[8];
            vector[17] = colours[9];
            vector[18] = colours[10];
            vector[19] = colours[11];

            vector[20] = e.getSharpness();
            vector[21] = e.getEdgeDensity();
            vector[22] = e.getEntropy();

            imageProfileObject.getSectionVectors().add(vector);
        }
    }


    public void generateGlobalVector(ImageProfileObject imageProfileObject) {

        EmbeddedVector e = imageProfileObject.getGlobalEmbeddedVector();

        float[] vector = new float[23];

        vector[0] = e.getBrightnessMean();
        vector[1] = e.getBrightnessDev();
        vector[2] = e.getDynamicRange();

        vector[3] = e.getHighlights();
        vector[4] = e.getShadows();

        vector[5] = e.getSaturationMean();
        vector[6] = e.getSaturationDev();
        vector[7] = e.getColourCoverage();

        float[] colours = e.getColours();

        vector[8] = colours[0];
        vector[9] = colours[1];
        vector[10] = colours[2];
        vector[11] = colours[3];
        vector[12] = colours[4];
        vector[13] = colours[5];
        vector[14] = colours[6];
        vector[15] = colours[7];
        vector[16] = colours[8];
        vector[17] = colours[9];
        vector[18] = colours[10];
        vector[19] = colours[11];

        vector[20] = e.getSharpness();
        vector[21] = e.getEdgeDensity();
        vector[22] = e.getEntropy();

        imageProfileObject.setGlobalVector(vector);
    }

    private float calculateCoSineSim(float[] vector1, float[] vector2){

        float numerator = 0;
        float vector1Mag = 0;
        float vector2Mag = 0;

        for(int i = 0; i < vector1.length; i++){

            numerator += vector1[i] * vector2[i];
            vector1Mag += vector1[i] * vector1[i];
            vector2Mag += vector2[i] * vector2[i];

        }

        vector1Mag = (float) Math.sqrt(vector1Mag);
        vector2Mag = (float) Math.sqrt(vector2Mag);

        return numerator / (vector1Mag * vector2Mag);

    }

    public void calculateRecommendationScore(float[] vector1, float[] vector2){

        ArrayList<float[]> separateVector1 = separateVector(vector1);
        ArrayList<float[]> separateVector2 = separateVector(vector2);

        float brightness = calculateCoSineSim(separateVector1.get(0), separateVector2.get(0));
        float saturation = calculateCoSineSim(separateVector1.get(1), separateVector2.get(1));
        float hues = calculateCoSineSim(separateVector1.get(2), separateVector2.get(2));
        float shadowsHighlights = calculateCoSineSim(separateVector1.get(3), separateVector2.get(3));
        float texture = calculateCoSineSim(separateVector1.get(4), separateVector2.get(4));


        System.out.println((brightness * (1/19) + (saturation * (1/19)) + (hues * (1/19)) + (shadowsHighlights * (1/19)) + (texture * (1/19))));

    }

    private ArrayList<float[]> separateVector(float[] vector) {

        float[] brightness = new float[3];
        float[] saturation = new float[3];
        float[] hues = new float[12];
        float[] shadowsHighlights = new float[2];
        float[] texture = new float[3];

        brightness[0] = vector[0];
        brightness[1] = vector[1];
        brightness[2] = vector[2];

        shadowsHighlights[0] = vector[3];
        shadowsHighlights[1] = vector[4];

        saturation[0] = vector[5];
        saturation[1] = vector[6];
        saturation[2] = vector[7];

        for (int i = 0; i < 12; i++) {
            hues[i] = vector[8 + i];
        }

        texture[0] = vector[20];
        texture[1] = vector[21];
        texture[2] = vector[22];

        ArrayList<float[]> separateVectors = new ArrayList<>();

        separateVectors.add(brightness);
        separateVectors.add(saturation);
        separateVectors.add(hues);
        separateVectors.add(shadowsHighlights);
        separateVectors.add(texture);

        return separateVectors;
    }
}
