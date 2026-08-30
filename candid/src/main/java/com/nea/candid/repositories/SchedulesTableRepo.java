package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.SchedulesTableEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class SchedulesTableRepo {

    private final JdbcTemplate jdbcTemplate;

    public SchedulesTableRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SchedulesTableEntity> getScheduleByDay(int dayOfWeek) {

        String sql = """
                select *
                from schedulestable
                where dayofweek = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->{

            return new SchedulesTableEntity(rs.getLong("scheduleid"), rs.getInt("startSlot"), rs.getInt("endSlot"), rs.getInt("dayofweek"), rs.getLong("photographerid"));

        }, dayOfWeek);

    }

    public int insertSchedule(int startSlot, int endSlot, int dayofweek) {

        String sql = """
                insert into schedulestable(startSlot, endSlot, dayofweek)
                values(?, ?, ?)
                """;

        return jdbcTemplate.update(sql, startSlot, endSlot, dayofweek);

    }

    public List<SchedulesTableEntity> getPhotographersSchedule(long photographer){

        String sql = """
                select *
                from schedulestable
                where photographer = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->{

            return new SchedulesTableEntity(rs.getLong("scheduleid"), rs.getInt("startSlot"), rs.getInt("endSlot"), rs.getInt("dayofweek"), rs.getLong("photographerid"));

        }, photographer);

    }

}
