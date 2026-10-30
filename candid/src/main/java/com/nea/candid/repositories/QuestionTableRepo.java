package com.nea.candid.repositories;

import com.nea.candid.data.dbEnties.QuestionsTableEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class QuestionTableRepo {

    private final JdbcTemplate jdbcTemplate;

    public QuestionTableRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int[] saveQuestion(List<QuestionsTableEntity> questionsTableEntities){

        String sql = """
                inser into questionstable(photographerid, question, active)
                values(?,?,?)
                """;

        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                ps.setLong(1, questionsTableEntities.get(i).getPhotographerid());
                ps.setString(2, questionsTableEntities.get(i).getQuestion());
                ps.setBoolean(3, questionsTableEntities.get(i).isActive());

            }

            @Override
            public int getBatchSize() {
                return questionsTableEntities.size();
            }
        });

    }

    public int markInactive(List<Long> questionids) {

        String ids = "" + questionids.getFirst();

        for (int i = 0; i < questionids.size(); i++) {

            ids = ids + "," + questionids.get(i);

        }

        String sql = """
                update questionstable
                set active = false
                where questionid in (""" + ids + """
                )
                """;

        return jdbcTemplate.update(sql);

    }

    public List<QuestionsTableEntity> getActiveQuestions(long photographerid) {

        String sql = """
                select *
                from questionstable
                where photographerid = ?
                and active = true
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            return new QuestionsTableEntity(
                    rs.getBoolean("active"),
                    rs.getString("question"),
                    rs.getLong("photographerid"),
                    rs.getLong("questionid")

            );

        }, photographerid);

    }

}
