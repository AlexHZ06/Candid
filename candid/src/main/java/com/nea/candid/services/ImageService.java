package com.nea.candid.services;

import com.nea.candid.data.dbEnties.TagsTableEntity;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.services.database.PhotosTableService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class ImageService {

    private final PhotosTableService photosTableService;

    public ImageService(PhotosTableService photosTableService) {
        this.photosTableService = photosTableService;
    }

    public ResponseBody uplaodImage(MultipartFile file, String photoName, long userId, String[] tags) {

        System.out.println("ran");
        final String[] numbList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        String newName = photoName + ".jpg";

        try {

            Path path = Paths.get("C:\\Users\\Alexander Hernandez\\Desktop\\Programing\\Projects\\Candid\\Storage\\Test\\" + newName);

            boolean pathExists = Files.exists(path);
            while (pathExists) {

                String addon = "";
                for (int i = 0; i < 5; i++) {

                    Random random = new Random();
                    addon = addon + numbList[random.nextInt(numbList.length)];

                }

                newName = photoName + addon + ".jpg";
                path = Paths.get("C:\\Users\\Alexander Hernandez\\Desktop\\Programing\\Projects\\Candid\\Storage\\Test\\" + newName);
                pathExists = Files.exists(path);


            }

            Files.createDirectories(path.getParent());
            BufferedImage image = ImageIO.read(file.getInputStream());

            ImageIO.write(image, "jpg", path.toFile());

            System.out.println(file.getSize());

            long photoid = photosTableService.addToPhotosTable(userId, photoName, "", "", LocalDateTime.now(), path.toString(), "", file.getSize(), image.getWidth(), image.getHeight());
            saveTags(tags, photoid);
            return new ResponseBody("imaged saved", 351, true);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ResponseBody("issue saving image", 301, false);
        }

    }

    public void saveTags(String[] tags, long photoid){

        photosTableService.addTags(tags);
        List<Long> tagids = photosTableService.getTagIds(tags);
        int[] result = photosTableService.addPhotoTags(photoid, tagids);
        for(int i = 0; i < result.length; i++){

            System.out.println(result[i]);

        }

    }


}
