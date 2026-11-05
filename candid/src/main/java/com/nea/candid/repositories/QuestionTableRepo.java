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

    public int saveQuestion(QuestionsTableEntity entity){

        String sql = """
                insert into questionstable(photographerid, question, active)
                values(?,?,true)
                """;

        return jdbcTemplate.update(sql, entity.getPhotographerid(), entity.getQuestion());

    }

    public int markInactive(long questionId) {


        String sql = """
                update questionstable
                set active = false
                where questionid = ?
                """;

        return jdbcTemplate.update(sql, questionId);

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
