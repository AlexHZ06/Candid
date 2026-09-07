package com.nea.candid.api.controllers;

import com.nea.candid.services.GeoCodingService;
import org.springframework.web.bind.annotation.*;
import com.nea.candid.data.dto.ResponseBody;

import java.util.Map;

@RestController
@RequestMapping("/geo")
public class GeoCodingController {

    private final GeoCodingService geoCodingService;

    public GeoCodingController(GeoCodingService geoCodingService) {
        this.geoCodingService = geoCodingService;
    }

    @PostMapping("/getcoord")
    public ResponseBody getCoord(@RequestBody Map<String, String> body){

        try {
            double[] coord = geoCodingService.getCoordinates(body.get("address"));
            if(coord == null){

                return ResponseBody.error("Adrress is null", 904);

            }
            else return ResponseBody.success(coord, 954);

        } catch (Exception e) {

            return ResponseBody.error(e.getMessage(), 904);

        }

    }

}
