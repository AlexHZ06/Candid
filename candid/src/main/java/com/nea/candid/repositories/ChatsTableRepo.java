package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.ChatsTableEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ChatsTableRepo {

    private final JdbcTemplate jdbcTemplate;

    public ChatsTableRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addChat(String message, long bookingId, LocalDateTime timesent){

        String sql = """
                insert into chatstable(chatmessage, bookingid, timesent)
                values(?, ?, ?)
                """;

        return jdbcTemplate.update(sql, message, bookingId, timesent);

    }

    public List<ChatsTableEntity> getChats(long bookingid) {

        String sql = """
                Select *
                from chatstable 
                where bookingid = ?;
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            return new ChatsTableEntity(
                    rs.getLong("chatid"),
                    rs.getString("chatmessage"),
                    rs.getLong("bookingid"),
                    rs.getTimestamp("timesent")
            );

        });

    }


}
