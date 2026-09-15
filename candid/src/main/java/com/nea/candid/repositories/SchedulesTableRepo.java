package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.SchedulesTableEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class SchedulesTableRepo {

    private final JdbcTemplate jdbcTemplate;

    public SchedulesTableRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SchedulesTableEntity> getScheduleByDay(int dayOfWeek, long photographerId) {

        String sql = """
                select *
                from schedulestable
                where dayofweek = ?
                and photographerid = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->{

            return new SchedulesTableEntity(rs.getLong("scheduleid"), rs.getInt("startSlot"), rs.getInt("endSlot"), rs.getInt("dayofweek"), rs.getLong("photographerid"));

        }, dayOfWeek, photographerId);

    }

    public int clearSchedule(long photographerId) {

        String sql = """
                delete from schedulestable
                where photographerid = ?
                """;

        return jdbcTemplate.update(sql, photographerId);

    }

    public int[] insertNewSchedules(List<SchedulesTableEntity> schedulesTableEntities) {

        String sql = """
                insert into schedulestable(startSlot, endSlot, dayofweek, photographerid)
                values(?, ?, ?, ?)
                """;

        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                ps.setInt(1, schedulesTableEntities.get(i).getStartSlot());
                ps.setInt(2, schedulesTableEntities.get(i).getEndSlot());
                ps.setInt(3, schedulesTableEntities.get(i).getDayofweek());
                ps.setLong(4, schedulesTableEntities.get(i).getPhotographerid());

            }

            @Override
            public int getBatchSize() {
                return schedulesTableEntities.size();
            }
        });
    }

    public int insertSchedule(int startSlot, int endSlot, int dayofweek, long photographerId) {

        String sql = """
                insert into schedulestable(startSlot, endSlot, dayofweek, photographerid)
                values(?, ?, ?, ?)
                """;

        return jdbcTemplate.update(sql, startSlot, endSlot, dayofweek, photographerId);

    }

    public List<SchedulesTableEntity> getPhotographersSchedule(long photographer){

        String sql = """
                select *
                from schedulestable
                where photographerid = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->{

            return new SchedulesTableEntity(rs.getLong("scheduleid"), rs.getInt("startSlot"), rs.getInt("endSlot"), rs.getInt("dayofweek"), rs.getLong("photographerid"));

        }, photographer);

    }

}
