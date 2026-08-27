package com.nea.candid.data.dataObjects;

import com.nea.candid.data.dbEnties.PhotosTableEntity;

public class ImageScore {

    private final PhotosTableEntity photosTableEntity;
    private final float score;

    public ImageScore(PhotosTableEntity photosTableEntity, float score) {
        this.photosTableEntity = photosTableEntity;
        this.score = score;
    }

    public PhotosTableEntity getPhotosTableEntity() {
        return photosTableEntity;
    }

    public float getScore() {
        return score;
    }
}
