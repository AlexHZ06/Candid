package com.nea.candid.api.controllers;

import com.nea.candid.data.dbEnties.AlbumsTableEntity;
import com.nea.candid.data.dbEnties.PhotosTableEntity;
import com.nea.candid.services.AlbumService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.nea.candid.data.dto.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/album")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }


    @PostMapping("/photographer/getphotos")
    public ResponseBody getAllPhotoFromAlbum(@RequestBody Map<String, Long> body){

        return albumService.getAllPhotosFromAlbum(body.get("albumId"));

    }

    @DeleteMapping("/photographer/deletephoto")
    public ResponseBody removeFromAlbum(@RequestBody Map<String, Long> body){

        return albumService.removeFromAlbum(body.get("albumId"), body.get("photoId"));

    }

    @PostMapping("/photographer/createalbum")
    public ResponseBody createAlbum(HttpServletRequest request, @RequestPart MultipartFile file, @RequestPart String name, @RequestPart String description){

        return albumService.createAlbum(Long.parseLong((String) request.getAttribute("userId")), name, file, description);

    }

    @PostMapping("/photographer/editalbum")
    public ResponseBody editAlbum(@RequestParam long albumId, @RequestPart MultipartFile file, @RequestParam String name, @RequestParam String description, @RequestParam String url, @RequestParam boolean updateThumbnail){

        return albumService.editAlbum(albumId, name, url,description, updateThumbnail, file);

    }

    @GetMapping("/photographer/getallalbums")
    public ResponseBody getAllAlbums(HttpServletRequest request){

        return albumService.getAllAlbums(Long.parseLong((String) request.getAttribute("userId")));

    }

    @PostMapping("/photographer/getalbum")
    public ResponseBody getAlbum(@RequestBody Map<String, Long> body){

        return albumService.getAlbum(body.get("albumId"));

    }

    @PostMapping("/photographer/deletealbum")
    public ResponseBody deleteAlbum(@RequestBody Map<String, Long> body){

        return albumService.deleteAlbum(body.get("albumId"));

    }


}
