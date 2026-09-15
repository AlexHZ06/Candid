package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.BookingSlotsTableEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
                    rs.getString("status")
            );

        }, photographerid);

    }

    public int addSlot(int startslot, int endslot, Date dateofshoot, long clinetid, long photographerid, String status) {

        String sql = """
                Insert into bookingslotstabl(startslot, endslot, dateofshoot, clinetid, photographerid, status)
                values(?,?,?,?,?,?)
                """;

        return jdbcTemplate.update(sql, startslot, endslot, dateofshoot, clinetid, photographerid, status);

    }

}
