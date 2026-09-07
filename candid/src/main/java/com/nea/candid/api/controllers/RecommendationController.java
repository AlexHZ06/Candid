package com.nea.candid.api.controllers;

import com.nea.candid.services.RecommendationService;
import jakarta.servlet.http.HttpServletRequest;
import com.nea.candid.data.dto.ResponseBody;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reco")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }


    @PostMapping("/client/updatevector")
    public ResponseBody updatePreferenceVectors(HttpServletRequest request, @RequestParam long photoId, @RequestParam String interaction ){

        return recommendationService.reCalcPreferenceVector((Long)request.getAttribute("userId"), photoId, interaction);

    }

    @PostMapping("/client/getpreferencephotos")
    public ResponseBody getPreferencePhotos(@RequestBody Map<String, Integer> body){

        return recommendationService.getPreferencePhotos(body.get("amount"));

    }

}
