package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.BookingSlotsTableEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public class BookingSlotsTableRepo {

    private final JdbcTemplate jdbcTemplate;

    public BookingSlotsTableRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public List<BookingSlotsTableEntity> getSlotsOfPhotographer(long photographerid){

        String sql = """
                select *
                from  bookingslotstable
                where photographerid = ?;
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            return new BookingSlotsTableEntity(
                    rs.getLong("slotid"),
                    rs.getInt("startslot"),
                    rs.getInt("endslot"),
                    rs.getDate("dateofshoot"),
                    rs.getLong("clientid"),
                    rs.getLong("photographerid"),
                    rs.getString("status"),
                    rs.getString("message"),
                    rs.getString("address"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude"),
                    rs.getString("category")
            );

        }, photographerid);

    }

    public int setStatus (String status, long bookingId){

        String sql = """
                update bookingslotstable
                set status = ?
                where slotid = ?;
                """;

        return jdbcTemplate.update(sql, status, bookingId);

    }

    public List<BookingSlotsTableEntity> getSlotsOfPhotographerWeek(long photographerid, LocalDate date){

        LocalDate endDate = date.plusDays(6);

        String sql = """
                select *
                from bookingslotstable
                where photographerid = ?
                and dateofshoot Between ? and ?;
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            return new BookingSlotsTableEntity(
                    rs.getLong("slotid"),
                    rs.getInt("startslot"),
                    rs.getInt("endslot"),
                    rs.getDate("dateofshoot"),
                    rs.getLong("clientid"),
                    rs.getLong("photographerid"),
                    rs.getString("status"),
                    rs.getString("message"),
                    rs.getString("address"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude"),
                    rs.getString("category")
            );

        }, photographerid, date, endDate);

    }



    public int addSlot(int startslot, int endslot, Date dateofshoot, long clinetid, long photographerid, String status) {

        String sql = """
                Insert into bookingslotstable(startslot, endslot, dateofshoot, clientid, photographerid, status)
                values(?,?,?,?,?,?)
                returning slotid
                """;

        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, startslot, endslot, dateofshoot, clinetid, photographerid, status);
        if(id == null){

            return -1;

        }
        else{

            return id;

        }

    }

    public int addDetails(BookingSlotsTableEntity entity){

        String sql = """
                update bookingslotstable
                set message = ?,
                    address = ?,
                    latitude = ?,
                    longitude = ?,
                    category = ?
                where slotid = ?
            """;

        return jdbcTemplate.update(sql, entity.getMessage(), entity.getAddress(), entity.getLatitude(), entity.getLongitude(),entity.getCategory(), entity.getSlotid());

    }

}
