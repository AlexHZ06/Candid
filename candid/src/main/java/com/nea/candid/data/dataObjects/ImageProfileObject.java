package com.nea.candid.data.dataObjects;

import com.nea.candid.data.dbEnties.EmbeddedVectorTableEntity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageProfileObject {

    private long photoId;
    private BufferedImage image;
    private ArrayList<EmbeddedVectorTableEntity> embededVectors =  new ArrayList<>();

    public ImageProfileObject(BufferedImage image, long photoId) {
        this.image = image;
        this.photoId = photoId;
    }

    public BufferedImage getImage() {
        return image;
    }

    public ArrayList<EmbeddedVectorTableEntity> getEmbededVectors() {
        return embededVectors;
    }

    public long getPhotoId() {
        return photoId;
    }

    public void addEmbeddedVector(float[] brightness, float[] saturation, float[] hue, float edgeDensity, float aspectRatio, int sectionNumber) {

        embededVectors.add(new EmbeddedVectorTableEntity(
                sectionNumber,
                brightness[0],
                brightness[1],
                brightness[2],
                saturation[0],
                saturation[1],
                hue[0],
                hue[1],
                hue[2],
                hue[3],
                hue[4],
                hue[5],
                hue[6],
                hue[7],
                edgeDensity,
                aspectRatio
        ));

    }
}
