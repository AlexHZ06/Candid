package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.ResponsesTableEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ResponsesTableRepo {

    private final JdbcTemplate jdbcTemplate;

    public ResponsesTableRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ResponsesTableEntity> getAllResponses(long photographerId, long bookingId) {

        String sql = """
                select *
                from responsestable
                where photographerid = ?
                and bookingid = ?;
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            return new ResponsesTableEntity(
                    rs.getLong("responseid"),
                    rs.getString("response"),
                    rs.getLong("questionid"),
                    rs.getLong("bookingid")
            );

        }, photographerId, bookingId);

    }

    public int[] addResponse(List<ResponsesTableEntity> responsesTableEntities) {

        String sql = """
                insert into responsestable(response, questionid, bookingid)
                values(?,?,?)
                """;

        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                ps.setString(1, responsesTableEntities.get(i).getResponse());
                ps.setLong(2, responsesTableEntities.get(i).getQuestionid());
                ps.setLong(3, responsesTableEntities.get(i).getBookingid());

            }

            @Override
            public int getBatchSize() {
                return  responsesTableEntities.size();
            }
        });

    }

}
