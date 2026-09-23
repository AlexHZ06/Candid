package com.nea.candid.api.controllers;

import com.nea.candid.data.dbEnties.BookingSlotsTableEntity;
import com.nea.candid.data.dbEnties.SchedulesTableEntity;
import com.nea.candid.services.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.*;
import com.nea.candid.data.dto.ResponseBody;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/photographer/addschedule")
    public ResponseBody addScheduleSlot(HttpServletRequest request, @RequestBody Map<String, Integer> body){

        try{

            return bookingService.addScheduleBlock(body.get("startSlot"), body.get("endSlot"), body.get("dayOfShoot"), Long.parseLong(request.getAttribute("userId").toString()));

        }catch(Exception e){

            return ResponseBody.error(e.getMessage(), 999);

        }

    }

    @PostMapping("/client/getschedule")
    public ResponseBody getSchedule(@RequestBody Map<String, Long> body){

        return bookingService.getSchedule(body.get("userId"));

    }

    @PostMapping("/photographer/getschedule")
    public ResponseBody getSchedulePhotographer(HttpServletRequest request){

        return bookingService.getSchedule(Long.parseLong(request.getAttribute("userId").toString()));

    }

    @PostMapping("/photographer/saveschedule")
    public ResponseBody saveSchedule(HttpServletRequest request, @RequestBody Map<String, List<List<SchedulesTableEntity>>> body){


        return bookingService.addChangesToSchedule(body.get("data"), Long.parseLong(request.getAttribute("userId").toString()));

    }

    @PostMapping("/getbookingslots")
    public ResponseBody getBookingSlots(@RequestBody Map<String, Long> body){

        return bookingService.getBookingSlots(body.get("userId"));

    }

    @PostMapping("/client/requestslot")
    public ResponseBody requestSlot(HttpServletRequest request, @RequestBody BookingSlotsTableEntity entity){

        return bookingService.requestBookingSlot(entity, Long.parseLong(request.getAttribute("userId").toString()));


    }

}
