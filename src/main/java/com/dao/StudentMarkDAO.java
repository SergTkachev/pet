package com.dao;

import com.model.Mark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class StudentMarkDAO extends CommonDAO {
    private static StudentMarkDAO instance;

    private StudentMarkDAO() {

    }

    public static synchronized StudentMarkDAO getInstance() {
        if (instance == null) {
            instance = new StudentMarkDAO();
        }
        return instance;
    }


    public void insertStudentMarks(Connection connection, String studentId, List<Mark> marks) {
        String query = "INSERT INTO student_mark (student_id, " +
                "subject, " +
                "mark) " +
                "VALUES (?, ?, ?)";
        try (PreparedStatement ps = prepareInsert(connection, query)) {
            int i = 1;
            for (Mark mark : marks) {
                i = 1;
                ps.setInt(i++, Integer.valueOf(studentId));
                ps.setString(i++, mark.getSubject());
                ps.setInt(i++, Integer.valueOf(mark.getMark()));
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
