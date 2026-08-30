package com.nea.candid.api.controllers;

import com.nea.candid.services.BookingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nea.candid.data.dto.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/photographer/addschedule")
    public ResponseBody addScheduleSlot(@RequestBody Map<String, Integer> body){

        try{

            return bookingService.addScheduleBlock(body.get("startSlot"), body.get("endSlot"), body.get("dayOfShoot"));

        }catch(Exception e){

            return ResponseBody.error(e.getMessage(), 999);

        }

    }

}
