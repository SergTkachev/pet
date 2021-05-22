package com.dao;

import com.model.Faculty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationDAO extends CommonDAO {
    private static RegistrationDAO instance;

    private RegistrationDAO() {

    }

    public static synchronized RegistrationDAO getInstance() {
        if (instance == null) {
            instance = new RegistrationDAO();
        }
        return instance;
    }

    public int insertRegistration(Connection connection, String studentId, String facultyId) {
        int registrationId = -1;
        String query = "INSERT INTO registration (faculty_id, student_id) VALUES (?, ?)";
        try (PreparedStatement ps = prepareInsert(connection, query)) {
            int i = 1;
            ps.setInt(i++, Integer.valueOf(facultyId));
            ps.setInt(i++, Integer.valueOf(studentId));
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            registrationId = generatedKeys.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrationId;
    }

    public void deleteRegistrations(Connection connection) {
        String query = "DELETE FROM registration";
        try (PreparedStatement ps = prepare(connection, query)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertRegistrationWithMarks(String studentId, Faculty faculty) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            int registrationId = insertRegistration(connection, studentId, faculty.getId());
            ExamMarkDAO.getInstance().insertExamMarks(connection, registrationId, faculty);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
