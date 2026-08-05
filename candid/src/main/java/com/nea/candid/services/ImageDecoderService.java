package com.nea.candid.services;

import com.nea.candid.data.dataObjects.ImageProfileObject;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class ImageDecoderService {

    public void calcAndDisplayInfo(ImageProfileObject imageProfileObject) {

        int width = imageProfileObject.getImage().getWidth();
        int height = imageProfileObject.getImage().getHeight();

        float[] brightness = calculateSectionBrightness(0, 0, width, height, imageProfileObject.getImage());
        float[] saturation = calculateSectionSaturation(0, 0, width, height, imageProfileObject.getImage());
        float[] hue = calculateSectionHue(0, 0, width, height, imageProfileObject.getImage());
        float edgeDensity = calculateEdgeDensity(0, 0, width, height, imageProfileObject.getImage());
        float aspecRatio = calculateAspectRatio(imageProfileObject);

        System.out.println("brightness");
        System.out.println("mean: " + brightness[0]);
        System.out.println("deviation: " + brightness[1]);
        System.out.println("saturation");
        System.out.println("mean: " + saturation[0]);
        System.out.println("deviation: " + saturation[1]);
        System.out.println("hue");
        System.out.println("red: " + hue[0]);
        System.out.println("Yellow green: " + hue[1]);
        System.out.println("Green Cyan: " + hue[2]);
        System.out.println("Cyan Blue: " + hue[3]);
        System.out.println("Blue Purple: " + hue[4]);
        System.out.println("Purple Red: " + hue[5]);
        System.out.println("edgeDensity");
        System.out.println(edgeDensity);
        System.out.println("aspecRatio");
        System.out.println(aspecRatio);

    }

    private int[] convertArgb(int rgb) {

        String binary = String.format("%32s", Integer.toBinaryString(rgb)).replace(' ', '0');
        return new int[]{
                Integer.parseInt(binary.substring(0, 8), 2),
                Integer.parseInt(binary.substring(8, 16), 2),
                Integer.parseInt(binary.substring(16, 24), 2),
                Integer.parseInt(binary.substring(24, 32), 2),
        };

    }

    private float[] calculateSectionBrightness(int startX, int startY, int endX, int endY, BufferedImage image) {

        if(startX >= endX || startY >= endY) {

            throw new IllegalArgumentException("startX or endX out of bounds");

        }

        double xDiff = endX - startX;
        double yDiff = endY - startY;

        int totalXpix = Math.max(1, (int) (xDiff * 0.3));
        int totalYpix = Math.max(1, (int) (yDiff * 0.3));

        double xStep = xDiff / (totalXpix);
        double yStep = yDiff / (totalYpix);


        ArrayList<Float> sectionBrightness = new ArrayList<>();

        for (int i = 1; i < totalXpix + 1; i++) {

            for (int j = 1; j < totalYpix + 1; j++) {

                int x = Math.min(image.getWidth() - 1, (int) (startX + (i * xStep)));
                int y = Math.min(image.getHeight() - 1, (int) (startY + (j * yStep)));

                int[] argb = convertArgb(image.getRGB(x, y));

                float brightness = 0.2126f * argb[1] + 0.7152f * argb[2] + 0.0722f * argb[3];

                sectionBrightness.add(brightness);
            }
        }

        double total = 0;

        for (int i = 0; i < sectionBrightness.size(); i++) {

            total += sectionBrightness.get(i);

        }

        double mean = total / (totalXpix * totalYpix);
        double squaredDifference = 0;

        for (int i = 0; i < sectionBrightness.size(); i++) {

            squaredDifference += (sectionBrightness.get(i) - mean) * (sectionBrightness.get(i) - mean);

        }

        float brightnessDeviation = (float) Math.sqrt(squaredDifference / (totalXpix * totalYpix));

        return new float[]{(float) mean, brightnessDeviation};


    }

    private float[] calculateSectionSaturation(int startX, int startY, int endX, int endY, BufferedImage image) {

        if(startX >= endX || startY >= endY) {

            throw new IllegalArgumentException("startX or endX out of bounds");

        }

        double xDiff = endX - startX;
        double yDiff = endY - startY;

        int totalXpix = Math.max(1, (int) (xDiff * 0.3));
        int totalYpix = Math.max(1, (int) (yDiff * 0.3));

        double xStep = xDiff / (totalXpix);
        double yStep = yDiff / (totalYpix);

        ArrayList<Float> saturations = new ArrayList<>();

        for (int i = 1; i < totalXpix + 1; i++) {

            for (int j = 1; j < totalYpix + 1; j++) {

                int x = Math.min(image.getWidth() - 1, (int) (startX + (i * xStep)));
                int y = Math.min(image.getHeight() - 1, (int) (startY + (j * yStep)));

                int[] argb = convertArgb(image.getRGB(x, y));
                int max = -1;
                int min = 256;

                for (int k = 1; k < argb.length; k++) {

                    if (argb[k] > max) {

                        max = argb[k];

                    }
                    if (argb[k] < min) {

                        min = argb[k];

                    }

                }

                float saturation = 0;

                if (max != 0) {

                    saturation = (float) (max - min) / max;

                }

                saturations.add(saturation);

            }

        }

        double total = 0;

        for (int i = 0; i < saturations.size(); i++) {

            total += saturations.get(i);

        }

        double mean = (total / (totalXpix * totalYpix));

        double squaredDifference = 0;

        for (int i = 0; i < saturations.size(); i++) {

            squaredDifference += (saturations.get(i) - mean) * (saturations.get(i) - mean);

        }

        double saturationStdDev = Math.sqrt(squaredDifference / (totalXpix * totalYpix));

        return new float[]{(float) mean, (float) saturationStdDev};

    }

    private float[] calculateSectionHue(int startX, int startY, int endX, int endY, BufferedImage image) {

        float red = 0;
        float yellowGreen = 0;
        float greenCyan = 0;
        float cyanBlue = 0;
        float bluePurple = 0;
        float purpleRed = 0;

        if(startX >= endX || startY >= endY) {

            throw new IllegalArgumentException("startX or endX out of bounds");

        }


        double xDiff = endX - startX;
        double yDiff = endY - startY;

        int totalXpix = Math.max(1, (int) (xDiff * 0.3));
        int totalYpix = Math.max(1, (int) (yDiff * 0.3));

        int totalValidPix = 0;

        double xStep = xDiff / (totalXpix);
        double yStep = yDiff / (totalYpix);

        for (int i = 1; i < totalXpix + 1; i++) {

            for (int j = 1; j < totalYpix + 1; j++) {

                int x = Math.min(image.getWidth() - 1, (int) (startX + (i * xStep)));
                int y = Math.min(image.getHeight() - 1, (int) (startY + (j * yStep)));

                int[] argb = convertArgb(image.getRGB(x, y));

                int maxRgb = -1;
                int minRgb = 256;

                int highestIndex = 0;

                for(int k = 1; k < argb.length; k++) {

                    if(argb[k] > maxRgb) {

                        maxRgb = argb[k];
                        highestIndex = k;

                    }
                    if(argb[k] < minRgb) {

                        minRgb = argb[k];

                    }

                }

                int dif = maxRgb - minRgb;

                if(maxRgb == 0) {

                    continue;

                }

                float sat = (float) (dif) / maxRgb;

                if(!(sat <= 0.15)) {

                    totalValidPix++;
                    float hue = 0;

                    if(highestIndex == 1) {

                       hue = (

                                (float) 60 * ((float) (argb[2] - argb[3]) / dif)

                        );

                        if(hue < 0){

                            hue = hue + 360;

                        }
                        if(hue > 360) {

                            hue = 360;

                        }

                    }
                    else if(highestIndex == 2) {

                        hue = (

                                (float) 60 * (((float) (argb[3] - argb[1]) / dif) + 2)

                        );

                        if(hue < 0){

                            hue = hue + 360;

                        }
                        if(hue > 360) {

                            hue = 360;

                        }

                    }
                    else if(highestIndex == 3) {

                        hue = (

                                (float) 60 * (((float) (argb[1] - argb[2]) / dif) + 4)

                        );

                        if(hue < 0){

                            hue = hue + 360;

                        }
                        if(hue > 360) {

                            hue = 360;

                        }

                    }

                    if (hue >= 0 && hue < 60) {
                        red += 1;
                    } else if (hue >= 60 && hue < 120) {
                        yellowGreen += 1;
                    } else if (hue >= 120 && hue < 180) {
                        greenCyan += 1;
                    } else if (hue >= 180 && hue < 240) {
                        cyanBlue += 1;
                    } else if (hue >= 240 && hue< 300) {
                        bluePurple += 1;
                    } else if (hue >= 300 && hue < 360) {
                        purpleRed += 1;
                    }

                }
            }
        }

        if(totalValidPix == 0) {

            return new float[]{0,0,0,0,0,0};

        }

        red = red /  totalValidPix;
        yellowGreen = yellowGreen / totalValidPix;
        greenCyan = greenCyan / totalValidPix;
        cyanBlue = cyanBlue / totalValidPix;
        bluePurple = bluePurple / totalValidPix;
        purpleRed = purpleRed / totalValidPix;

        return new float[]{red, yellowGreen, greenCyan, cyanBlue, bluePurple, purpleRed};

    }

    public float calculateAspectRatio(ImageProfileObject imageProfileObject) {

        int width = imageProfileObject.getImage().getWidth();
        int height = imageProfileObject.getImage().getHeight();

        return (float) width /height;

    }

    private float calculateEdgeDensity(int startX, int startY, int endX, int endY, BufferedImage image) {

        if(startX >= endX || startY >= endY) {

            throw new IllegalArgumentException("startX or endX out of bounds");

        }

        double xDiff = endX - startX;
        double yDiff = endY - startY;

        int totalXpix = Math.max(1, (int) (xDiff * 0.3));
        int totalYpix = Math.max(1, (int) (yDiff * 0.3));

        double xStep = xDiff / (totalXpix);
        double yStep = yDiff / (totalYpix);

        double edgeCount = 0;

        int pixelComp = 0;

        for (int i = 1; i < totalXpix + 1; i++) {

            for (int j = 1; j < totalYpix + 1; j++) {

                int x = Math.min(image.getWidth() - 1, (int) (startX + (i * xStep)));
                int y = Math.min(image.getHeight() - 1, (int) (startY + (j * yStep)));

                int[] pixelArgb = convertArgb(image.getRGB(x, y));
                float pixelBrightness = 0.2126f * pixelArgb[1] + 0.7152f * pixelArgb[2] + 0.0722f * pixelArgb[3];

                if(x + 1 < image.getWidth()){

                    pixelComp++;
                    int[] rightArgb = convertArgb(image.getRGB(x + 1, y));
                    float brightness = 0.2126f * rightArgb[1] + 0.7152f * rightArgb[2] + 0.0722f * rightArgb[3];

                    float diff = pixelBrightness -  brightness;
                    if(diff < 0) {diff = diff * -1;}
                    if(diff >= 30){edgeCount++;}

                }
                if(y + 1 < image.getHeight()){

                    pixelComp++;
                    int[] downArgb =  convertArgb(image.getRGB(x, y + 1));
                    float brightness = 0.2126f * downArgb[1] + 0.7152f * downArgb[2] + 0.0722f * downArgb[3];

                    float diff = pixelBrightness -  brightness;
                    if(diff < 0) {diff = diff * -1;}
                    if(diff >= 30){edgeCount++;}

                }
            }
        }

        if(pixelComp == 0){

            return 0;

        }

        return (float) edgeCount / pixelComp;

    }

}
