package com.nea.candid.services;

import com.nea.candid.data.dbEnties.BookingSlotsTableEntity;
import com.nea.candid.data.dbEnties.SchedulesTableEntity;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.services.database.BookingDbService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {

    private final BookingDbService bookingDbService;

    public BookingService(BookingDbService bookingDbService) {
        this.bookingDbService = bookingDbService;
    }

    public ResponseBody addScheduleBlock(int start, int end, int dayofshoot){

        if(start < 0 || start == 48){

            throw new IllegalArgumentException("start cannot be less than 0 or equal to 48");

        }
        else if(end > 48 || end == 0){

            throw new IllegalArgumentException("end cannot be greater than 48 or equal to 0");

        }
        else if(start > end){

            throw new IllegalArgumentException("start cannot be greater than end");

        }
        else if(start == end){

            throw new IllegalArgumentException("start cannot be equal to end");

        }
        else if(dayofshoot < 0 || dayofshoot > 7){

            throw new IllegalArgumentException("dayofshoot cannot be less than 0 or equal to 7");

        }

        List<SchedulesTableEntity> schedule = bookingDbService.getScheduleByDay(dayofshoot);
        boolean clash = false;
        if(!schedule.isEmpty()){

            int index = 0;

            while(index < schedule.size() && !clash){

                if(start <= schedule.get(index).getStartSlot() && end >= schedule.get(index).getEndSlot()){

                    clash = true;

                }
                else if(start >= schedule.get(index).getStartSlot() && end <= schedule.get(index).getEndSlot()){

                    clash = true;

                }
                else if(start <= schedule.get(index).getStartSlot() && end <= schedule.get(index).getEndSlot() && end >= schedule.get(index).getStartSlot()){

                    clash = true;

                }
                else if(start >= schedule.get(index).getStartSlot() && end >= schedule.get(index).getEndSlot() && start <= schedule.get(index).getEndSlot()){

                    clash = true;

                }
                else if(schedule.get(index).getStartSlot() == start && schedule.get(index).getEndSlot() == end){

                    clash = true;

                }

                index++;

            }

            if(!clash){

                bookingDbService.insertIntoSchedule(start, end, dayofshoot);
                return ResponseBody.success("Slot added", 505);

            }
            else{

                return ResponseBody.error("Slot clash", 505);

            }


        }
        else {
            bookingDbService.insertIntoSchedule(start, end, dayofshoot);
            return ResponseBody.success("Slot added", 505);
        }

    }
//
//    public ResponseBody removeScheduleSlot(int startSlot, int endSlot, int dayofshoot){
//
//
//
//    }



    public ResponseBody requestBookingSlot(int duration, int start, long photographer, LocalDate dayOfShoot, long client){

        //Check if the schedule has that slot
        List<SchedulesTableEntity> schedule = bookingDbService.getPhotographerSchedule(photographer);
        if(schedule.isEmpty()){

            throw new IllegalArgumentException("photographer cannot has no schedule");

        }

        int end = start + duration;
        boolean valid = false;
        int index = 0;

        while(index < schedule.size() && !valid){

            if((start >= schedule.get(index).getStartSlot() && end <= schedule.get(index).getEndSlot() && schedule.get(index).getDayofweek() == dayOfShoot.getDayOfWeek().getValue())){

                valid = true;

            }

            index++;

        }

        if(valid){

            List<BookingSlotsTableEntity> slots = bookingDbService.getPhotographersSlots(photographer);

            if(slots.isEmpty()){

                Date date = Date.from(dayOfShoot.atStartOfDay(ZoneId.systemDefault()).toInstant());

                bookingDbService.addSlot(start, end, date, client, photographer, "pending");
                return ResponseBody.success("Slot added", 556);

            }

            index = 0;
            while(index < slots.size() && valid){

                LocalDate shootDate = slots.get(index).getDayofshoot().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if(start <= slots.get(index).getStartslot() && end >= slots.get(index).getEndslot() && shootDate.equals(dayOfShoot)){

                    valid = false;

                }
                else if(start >= slots.get(index).getStartslot() && end <= slots.get(index).getEndslot() && shootDate.equals(dayOfShoot)){

                    valid = false;

                }
                else if(start <= slots.get(index).getStartslot() && end <= slots.get(index).getEndslot() && end >= slots.get(index).getStartslot() && shootDate.equals(dayOfShoot)){

                    valid = false;

                }
                else if(start >= slots.get(index).getStartslot() && end >= slots.get(index).getEndslot() && start <= slots.get(index).getEndslot() && shootDate.equals(dayOfShoot)){

                    valid = false;

                }
                else if(slots.get(index).getStartslot() == start && slots.get(index).getEndslot() == end && shootDate.equals(dayOfShoot)){

                    valid = false;

                }

                index++;

            }

            if(valid){

                Date date = Date.from(dayOfShoot.atStartOfDay(ZoneId.systemDefault()).toInstant());

                bookingDbService.addSlot(start, end, date, client, photographer, "pending");
                return ResponseBody.success("Slot added", 556);

            }
            else {
                return ResponseBody.error("Slot clash", 556);
            }

        }
        else{

            return ResponseBody.error("no Slots", 556);

        }
    }


}
