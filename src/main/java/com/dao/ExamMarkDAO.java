package com.dao;

import com.model.Faculty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExamMarkDAO extends CommonDAO {
    private static ExamMarkDAO instance;

    private ExamMarkDAO() {

    }

    public static synchronized ExamMarkDAO getInstance() {
        if (instance == null) {
            instance = new ExamMarkDAO();
        }
        return instance;
    }

    public void insertExamMarks(Connection connection, int registrationId, Faculty faculty) {
        String query = "INSERT INTO exam_mark (registration_id, faculty_exam_id, mark) VALUES (?, ?, ?)";
        try (PreparedStatement ps = prepare(connection, query)) {
            for (int j = 0; j < faculty.getExams().size(); j++) {
                int i = 1;
                ps.setInt(i++, registrationId);
                ps.setInt(i++, Integer.valueOf(faculty.getExams().get(j).getId()));
                ps.setInt(i++, Integer.valueOf(faculty.getExams().get(j).getExammark()));
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
