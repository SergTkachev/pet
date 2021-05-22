package com.dao;

import com.model.FacultyExam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FacultyExamDAO extends CommonDAO {

    private static FacultyExamDAO instance;

    private FacultyExamDAO() {

    }

    public static synchronized FacultyExamDAO getInstance() {
        if (instance == null) {
            instance = new FacultyExamDAO();
        }
        return instance;
    }

    private static FacultyExam buildFacultyExam(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int facultyId = rs.getInt("faculty_id");
        String exam = rs.getString("exam");
        return new FacultyExam(String.valueOf(id),
                String.valueOf(facultyId),
                exam);
    }

    public List<FacultyExam> getAllByFacultyId(String facultyId) {
        List<FacultyExam> facultyExams = new ArrayList<>();
        String query = "SELECT * FROM faculty_exam WHERE faculty_id = ? ORDER BY exam";
        try (Connection connection = getConnection();
             PreparedStatement ps = prepare(connection, query)) {
            ps.setInt(1, Integer.parseInt(facultyId));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                facultyExams.add(buildFacultyExam(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facultyExams;
    }

    public FacultyExam get(String id) {
        FacultyExam facultyExam = null;
        String query = ("SELECT * FROM faculty_exam WHERE id = ?");
        try (Connection connection = getConnection();
             PreparedStatement ps = prepare(connection, query)) {
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                facultyExam = buildFacultyExam(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facultyExam;
    }

    public void add(FacultyExam facultyExam) {
        String query = "INSERT INTO faculty_exam (faculty_id, exam) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement ps = prepareInsert(connection, query)) {
            int i = 1;
            ps.setInt(i++, Integer.parseInt(facultyExam.getFacultyId()));
            ps.setString(i++, facultyExam.getExam());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            facultyExam.setId(String.valueOf(generatedKeys.getInt(1)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateList(List<FacultyExam> facultyExams) {
        String query = "UPDATE faculty_exam SET faculty_id = ?, exam = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = prepare(connection, query)) {
            for (FacultyExam facultyExam : facultyExams) {
                int i = 1;
                ps.setInt(i++, Integer.parseInt(facultyExam.getFacultyId()));
                ps.setString(i++, facultyExam.getExam());
                ps.setInt(i++, Integer.parseInt(facultyExam.getId()));
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        String query = "DELETE FROM faculty_exam WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = prepare(connection, query)) {
            ps.setInt(1, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void facultyExamNames(Map<Integer, String> names, String facultyId) {
        String query = "SELECT * FROM faculty_exam WHERE faculty_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = prepare(connection, query)) {
            ps.setInt(1, Integer.parseInt(facultyId));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                names.put(rs.getInt("id"), rs.getString("exam"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}