package com.dao;

import com.model.Faculty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FacultyDAO extends CommonDAO {

    private static FacultyDAO instance;

    private FacultyDAO() {

    }

    public static synchronized FacultyDAO getInstance() {
        if (instance == null) {
            instance = new FacultyDAO();
        }
        return instance;
    }

    private static Faculty buildFaculty(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int budget = rs.getInt("budget");
        int common = rs.getInt("common");
        return new Faculty(String.valueOf(id),
                name,
                String.valueOf(budget),
                String.valueOf(common));
    }

    public List<Faculty> getAll(String column, String direction) {
        List<Faculty> faculties = new ArrayList<>();
        String query = "SELECT * FROM faculty ORDER BY " + column + " " + direction;
        try (Connection connection = getConnection();
             PreparedStatement ps = prepare(connection, query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                faculties.add(buildFaculty(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faculties;
    }

    public Faculty get(String id) {
        Faculty faculty = null;
        String query = ("SELECT * FROM faculty WHERE id = ?");
        try (Connection connection = getConnection();
             PreparedStatement ps = prepare(connection, query)) {
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                faculty = buildFaculty(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faculty;
    }

    public void add(Faculty faculty) {
        String query = "INSERT INTO faculty (name, " +
                "budget, " +
                "common) " +
                "VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement ps = prepareInsert(connection, query)) {
            int i = 1;
            ps.setString(i++, faculty.getName());
            ps.setInt(i++, Integer.parseInt(faculty.getBudget()));
            ps.setInt(i++, Integer.parseInt(faculty.getCommon()));
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            faculty.setId(String.valueOf(generatedKeys.getInt(1)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateList(List<Faculty> faculties) {
        String query = "UPDATE faculty SET name = ?, budget = ?, common = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = prepare(connection, query)) {
            for (Faculty faculty : faculties) {
                int i = 1;
                ps.setString(i++, faculty.getName());
                ps.setInt(i++, Integer.parseInt(faculty.getBudget()));
                ps.setInt(i++, Integer.parseInt(faculty.getCommon()));
                ps.setInt(i++, Integer.parseInt(faculty.getId()));
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        String query = "DELETE FROM faculty WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = prepare(connection, query)) {
            ps.setInt(1, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void facultyNames(Map<Integer, String> names) {
        String query = "SELECT * FROM faculty";
        try (Connection connection = getConnection();
             PreparedStatement ps = prepare(connection, query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                names.put(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Faculty> getRegistrations(String userId, String column, String direction) {
        List<Faculty> faculties = new ArrayList<>();
        String query = "SELECT DISTINCT f.* FROM faculty f " +
                "INNER JOIN faculty_exam e ON e.faculty_id = f.id " +
                "WHERE f.id NOT IN (SELECT r.faculty_id FROM registration r WHERE r.student_id = ?) " +
                "ORDER BY " + column + " " + direction;
        try (Connection connection = getConnection();
             PreparedStatement ps = prepare(connection, query)) {
            ps.setInt(1, Integer.valueOf(userId));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                faculties.add(buildFaculty(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return faculties;
    }
}