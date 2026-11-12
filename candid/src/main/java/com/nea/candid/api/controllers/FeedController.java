package com.nea.candid.api.controllers;

import com.nea.candid.data.dataObjects.FeedRequestObject;
import com.nea.candid.services.FeedService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nea.candid.data.dto.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @PostMapping("/client/getfeed")
    public ResponseBody getFeed(@RequestBody FeedRequestObject body) {

        return feedService.getFeed(body);

    }

}
