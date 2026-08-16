package com.nea.candid.data.dbEnties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "embeddedvectorstable")
public class EmbeddedVectorTableEntity {

    @Id
    private long embeddedvectorid;
    private long photoid;
    private int sectionnumber;
    private float brightnessmean;
    private float brightnessdeviation;
    private float dynamicrange;
    private float saturationmean;
    private float saturationdeviation;
    private float red;
    private float orange;
    private float yellow;
    private float green;
    private float cyan;
    private float blue;
    private float purple;
    private float magenta;
    private float edgedensity;
    private float aspectratio;

    public EmbeddedVectorTableEntity(float aspectratio, float edgedensity, float magenta, float purple, float blue, float cyan, float green, float yellow, float orange, float red, float saturationdeviation, float saturationmean, float dynamicrange, float brightnessdeviation, float brightnessmean, int sectionnumber, long photoid, long embeddedvectorid) {
        this.aspectratio = aspectratio;
        this.edgedensity = edgedensity;
        this.magenta = magenta;
        this.purple = purple;
        this.blue = blue;
        this.cyan = cyan;
        this.green = green;
        this.yellow = yellow;
        this.orange = orange;
        this.red = red;
        this.saturationdeviation = saturationdeviation;
        this.saturationmean = saturationmean;
        this.dynamicrange = dynamicrange;
        this.brightnessdeviation = brightnessdeviation;
        this.brightnessmean = brightnessmean;
        this.sectionnumber = sectionnumber;
        this.photoid = photoid;
        this.embeddedvectorid = embeddedvectorid;
    }

    public EmbeddedVectorTableEntity(int sectionnumber, float brightnessmean, float brightnessdeviation, float dynamicrange, float saturationmean, float saturationdeviation, float red, float orange, float yellow, float green, float cyan, float blue, float purple, float magenta, float edgedensity) {
        this.sectionnumber = sectionnumber;
        this.brightnessmean = brightnessmean;
        this.brightnessdeviation = brightnessdeviation;
        this.dynamicrange = dynamicrange;
        this.saturationmean = saturationmean;
        this.saturationdeviation = saturationdeviation;
        this.red = red;
        this.orange = orange;
        this.yellow = yellow;
        this.green = green;
        this.cyan = cyan;
        this.blue = blue;
        this.purple = purple;
        this.magenta = magenta;
        this.edgedensity = edgedensity;
    }

    public EmbeddedVectorTableEntity(int sectionnumber, float brightnessmean, float brightnessdeviation, float dynamicrange, float saturationmean, float saturationdeviation, float red, float orange, float yellow, float green, float cyan, float blue, float purple, float magenta, float edgedensity, float aspectratio) {
        this.sectionnumber = sectionnumber;
        this.brightnessmean = brightnessmean;
        this.brightnessdeviation = brightnessdeviation;
        this.dynamicrange = dynamicrange;
        this.saturationmean = saturationmean;
        this.saturationdeviation = saturationdeviation;
        this.red = red;
        this.orange = orange;
        this.yellow = yellow;
        this.green = green;
        this.cyan = cyan;
        this.blue = blue;
        this.purple = purple;
        this.magenta = magenta;
        this.edgedensity = edgedensity;
        this.aspectratio = aspectratio;
    }

    public long getEmbeddedvectorid() {
        return embeddedvectorid;
    }

    public void setEmbeddedvectorid(long embeddedvectorid) {
        this.embeddedvectorid = embeddedvectorid;
    }

    public long getPhotoid() {
        return photoid;
    }

    public void setPhotoid(long photoid) {
        this.photoid = photoid;
    }

    public int getSectionnumber() {
        return sectionnumber;
    }

    public void setSectionnumber(int sectionnumber) {
        this.sectionnumber = sectionnumber;
    }

    public float getBrightnessmean() {
        return brightnessmean;
    }

    public void setBrightnessmean(float brightnessmean) {
        this.brightnessmean = brightnessmean;
    }

    public float getBrightnessdeviation() {
        return brightnessdeviation;
    }

    public void setBrightnessdeviation(float brightnessdeviation) {
        this.brightnessdeviation = brightnessdeviation;
    }

    public float getDynamicrange() {
        return dynamicrange;
    }

    public void setDynamicrange(float dynamicrange) {
        this.dynamicrange = dynamicrange;
    }

    public float getSaturationmean() {
        return saturationmean;
    }

    public void setSaturationmean(float saturationmean) {
        this.saturationmean = saturationmean;
    }

    public float getSaturationdeviation() {
        return saturationdeviation;
    }

    public void setSaturationdeviation(float saturationdeviation) {
        this.saturationdeviation = saturationdeviation;
    }

    public float getRed() {
        return red;
    }

    public void setRed(float red) {
        this.red = red;
    }

    public float getOrange() {
        return orange;
    }

    public void setOrange(float orange) {
        this.orange = orange;
    }

    public float getYellow() {
        return yellow;
    }

    public void setYellow(float yellow) {
        this.yellow = yellow;
    }

    public float getGreen() {
        return green;
    }

    public void setGreen(float green) {
        this.green = green;
    }

    public float getCyan() {
        return cyan;
    }

    public void setCyan(float cyan) {
        this.cyan = cyan;
    }

    public float getBlue() {
        return blue;
    }

    public void setBlue(float blue) {
        this.blue = blue;
    }

    public float getPurple() {
        return purple;
    }

    public void setPurple(float purple) {
        this.purple = purple;
    }

    public float getMagenta() {
        return magenta;
    }

    public void setMagenta(float magenta) {
        this.magenta = magenta;
    }

    public float getEdgedensity() {
        return edgedensity;
    }

    public float getAspectratio() {
        return aspectratio;
    }
}
