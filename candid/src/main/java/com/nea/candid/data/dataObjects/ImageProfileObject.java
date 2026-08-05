package com.nea.candid.data.dataObjects;

import java.awt.image.BufferedImage;

public class ImageProfileObject {

    private BufferedImage image;
    private float brightness;

    public ImageProfileObject(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }
}
