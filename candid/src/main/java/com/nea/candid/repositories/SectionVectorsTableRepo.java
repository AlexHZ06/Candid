package com.nea.candid.repositories;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SectionVectorsTableRepo {

    private final JdbcTemplate jdbcTemplate;

    public SectionVectorsTableRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int[] insertSectionVectors(long photoId, ArrayList<float[]> embeddedVectors){

        String sql = """
                Insert into sectionvectorstable(photoid, sectionnum, embeddedvector)
                values(?, ?, ?)
                """;

        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                Float[] vector = new Float[embeddedVectors.get(i).length];

                for(int j = 0; j < vector.length; j++){
                    vector[j] =  embeddedVectors.get(i)[j];
                }

                Array array = ps.getConnection().createArrayOf("float", vector);
                ps.setLong(1, photoId);
                ps.setInt(2, i);
                ps.setArray(3, array);

            }

            @Override
            public int getBatchSize() {
                return embeddedVectors.size();
            }
        });

    }

    public List<float[]> getSectionVectors(long ImageId){

        String sql = """
                select embeddedvector
                from sectionvectorstable
                where photoid = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->  {

            Array array = rs.getArray("embeddedvector");
            Float[] vectorList = (Float[]) array.getArray();
            float[] vector = new float[vectorList.length];
            for(int j = 0; j < vector.length; j++){
                vector[j] = vectorList[j];
            }
            return vector;

        }, ImageId);
    }

}
