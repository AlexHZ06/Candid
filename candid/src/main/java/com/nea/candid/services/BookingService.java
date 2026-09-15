package com.nea.candid.services;

import com.nea.candid.data.dbEnties.BookingSlotsTableEntity;
import com.nea.candid.data.dbEnties.SchedulesTableEntity;
import com.nea.candid.data.dto.ResponseBody;
import com.nea.candid.repositories.SchedulesTableRepo;
import com.nea.candid.services.database.BookingDbService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {

    private final BookingDbService bookingDbService;

    public BookingService(BookingDbService bookingDbService) {
        this.bookingDbService = bookingDbService;
    }

    public ResponseBody addChangesToSchedule(List<List<SchedulesTableEntity>> schedules, long photographerId) {

        for(int i = 0; i < schedules.size(); i++) {

            for(int j = 0; j < schedules.get(i).size(); j++) {

                System.out.println(schedules.get(i).get(j).getStartSlot());

            }

        }

        List<SchedulesTableEntity> newSlots = new ArrayList<>();

        for(int i = 0; i < schedules.size(); i++) {


            if(schedules.get(i).isEmpty()) {
                continue;
            }

            schedules.get(i).sort(Comparator.comparingInt(SchedulesTableEntity::getStartSlot));
            boolean consec = false;
            int start = schedules.get(i).getFirst().getStartSlot();
            int end = 0;

            for(int j = 1; j < schedules.get(i).size(); j++) {

                if(schedules.get(i).get(j).getStartSlot() - schedules.get(i).get(j - 1).getStartSlot() == 1) {

                    consec = true;
                    end = schedules.get(i).get(j).getStartSlot();

                }
                else{

                    if(consec) {

                        newSlots.add(new SchedulesTableEntity(0, start, end, i, photographerId));

                    }
                    consec = false;

                }

                if(!consec){

                    start = schedules.get(i).get(j).getStartSlot();

                }


            }

            if(consec) {
                newSlots.add(new SchedulesTableEntity(0, start, end, i, photographerId));
            }

        }

        try{

            bookingDbService.clearSchedule(photographerId);
            bookingDbService.addSchedules(newSlots);
            return ResponseBody.success("saved schedule", 952);

        }catch(Exception e){

            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseBody.error("could not save schedule", 903);

        }

    }

    public ResponseBody addScheduleBlock(int start, int end, int dayofshoot, long photographerId){

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

        List<SchedulesTableEntity> schedule = bookingDbService.getScheduleByDay(dayofshoot, photographerId);
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

                bookingDbService.insertIntoSchedule(start, end, dayofshoot, photographerId);
                return ResponseBody.success("Slot added", 505);

            }
            else{

                return ResponseBody.error("Slot clash", 505);

            }


        }
        else {
            bookingDbService.insertIntoSchedule(start, end, dayofshoot, photographerId);
            return ResponseBody.success("Slot added", 505);
        }

    }
//
//    public ResponseBody removeScheduleSlot(int startSlot, int endSlot, int dayofshoot){
//
//
//
//    }



    public ResponseBody requestBookingSlot(BookingSlotsTableEntity bookingSlotsTableEntity, long client){

        int start = bookingSlotsTableEntity.getStartslot();
        int end = bookingSlotsTableEntity.getEndslot();
        long photographer = bookingSlotsTableEntity.getPhotographerid();
        LocalDate dateOfShoot = bookingSlotsTableEntity.getDayofshoot().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        List<SchedulesTableEntity> schedule = bookingDbService.getPhotographerSchedule(photographer);
        if(schedule.isEmpty()){

            throw new IllegalArgumentException("photographer cannot has no schedule");

        }

        boolean valid = false;
        int index = 0;

        while(index < schedule.size() && !valid){

            if((start >= schedule.get(index).getStartSlot() && end <= schedule.get(index).getEndSlot() && schedule.get(index).getDayofweek() == dateOfShoot.getDayOfWeek().getValue())){

                valid = true;

            }

            index++;

        }

        if(valid){

            List<BookingSlotsTableEntity> slots = bookingDbService.getPhotographersSlots(photographer);

            if(slots.isEmpty()){

                Date date = Date.from(dateOfShoot.atStartOfDay(ZoneId.systemDefault()).toInstant());

                bookingDbService.addSlot(start, end, date, client, photographer, "pending");
                return ResponseBody.success("Slot added", 556);

            }

            index = 0;
            while(index < slots.size() && valid){

                LocalDate shootDate = slots.get(index).getDayofshoot().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if(start <= slots.get(index).getStartslot() && end >= slots.get(index).getEndslot() && shootDate.equals(dateOfShoot)){

                    valid = false;

                }
                else if(start >= slots.get(index).getStartslot() && end <= slots.get(index).getEndslot() && shootDate.equals(dateOfShoot)){

                    valid = false;

                }
                else if(start <= slots.get(index).getStartslot() && end <= slots.get(index).getEndslot() && end >= slots.get(index).getStartslot() && shootDate.equals(dateOfShoot)){

                    valid = false;

                }
                else if(start >= slots.get(index).getStartslot() && end >= slots.get(index).getEndslot() && start <= slots.get(index).getEndslot() && shootDate.equals(dateOfShoot)){

                    valid = false;

                }
                else if(slots.get(index).getStartslot() == start && slots.get(index).getEndslot() == end && shootDate.equals(dateOfShoot)){

                    valid = false;

                }

                index++;

            }

            if(valid){

                Date date = Date.from(dateOfShoot.atStartOfDay(ZoneId.systemDefault()).toInstant());

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

    public ResponseBody getSchedule(long userId){

        try{

            return ResponseBody.success(bookingDbService.getPhotographerSchedule(userId), 955);

        }catch(Exception e){

            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseBody.error("no schedule", 905);

        }
    }

    public ResponseBody getBookingSlots(long userId){

        List<BookingSlotsTableEntity> bookingSlots = bookingDbService.getPhotographersSlots(userId);
        return ResponseBody.success(bookingSlots, 955);

    }

}
