package com.nea.candid.data.dto;

import com.nea.candid.data.dbEnties.PhotosTableEntity;

import java.awt.image.BufferedImage;

public class ImageData {

    private BufferedImage image;
    private PhotosTableEntity record;

    public ImageData(BufferedImage image, PhotosTableEntity record) {
        this.image = image;
        this.record = record;
    }

    public BufferedImage getImage() {
        return image;
    }

    public PhotosTableEntity getRecord() {
        return record;
    }
}
