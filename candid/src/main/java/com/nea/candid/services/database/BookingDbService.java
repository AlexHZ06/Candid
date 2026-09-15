package com.nea.candid.services.database;

import com.nea.candid.data.dbEnties.BookingSlotsTableEntity;
import com.nea.candid.data.dbEnties.SchedulesTableEntity;
import com.nea.candid.repositories.BookingSlotsTableRepo;
import com.nea.candid.repositories.SchedulesTableRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class BookingDbService {

    private final BookingSlotsTableRepo bookingSlotsTableRepo;
    private final SchedulesTableRepo schedulesTableRepo;

    public BookingDbService(BookingSlotsTableRepo bookingSlotsTableRepo, SchedulesTableRepo schedulesTableRepo) {
        this.bookingSlotsTableRepo = bookingSlotsTableRepo;
        this.schedulesTableRepo = schedulesTableRepo;
    }

    public List<SchedulesTableEntity> getScheduleByDay(int day, long photographerId) {

        return schedulesTableRepo.getScheduleByDay(day, photographerId);

    }

    public void insertIntoSchedule(int start, int end, int dayOfWeek, long photographerId) {

        int result = schedulesTableRepo.insertSchedule(start,end,dayOfWeek,photographerId);
        if(result == 0) {

            throw new RuntimeException("insert schedule failed");

        }

    }

    public List<SchedulesTableEntity> getPhotographerSchedule(long Photographer){

        return schedulesTableRepo.getPhotographersSchedule(Photographer);

    }

    public List<BookingSlotsTableEntity> getPhotographersSlots(long Photographer){

        return bookingSlotsTableRepo.getSlotsOfPhotographer(Photographer);

    }

    public void addSlot(int startslot, int endslot, Date dayofshoot, long clinetid, long photographerid, String status){

        int result = bookingSlotsTableRepo.addSlot(startslot,endslot,dayofshoot,clinetid,photographerid,status);
        if(result == 0)
            throw new RuntimeException("insert slot failed");

    }

    public void clearSchedule(long photographerid){

        int result = schedulesTableRepo.clearSchedule(photographerid);

    }

    public void addSchedules(List<SchedulesTableEntity> schedulesTableEntities){

        System.out.println("ran");
        int[] result = schedulesTableRepo.insertNewSchedules(schedulesTableEntities);
        for(int i = 0; i < result.length; i++){

            System.out.println( result[i]);

            if(result[i] == 0){

                throw new RuntimeException("insert schedule failed");

            }

        }

    }

}
