package com.dao;

import com.model.Mark;
import com.model.User;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.dao.FileDAO.UPLOAD_DIRECTORY;

public class UserDAO extends CommonDAO {
    private static UserDAO instance;

    private UserDAO() {

    }

    public static synchronized UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    private User buildUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String email = rs.getString("email");
        String firstName = rs.getString("first_name");
        String middleName = rs.getString("middle_name");
        String lastName = rs.getString("last_name");
        String district = rs.getString("district");
        String city = rs.getString("city");
        String school = rs.getString("school");
        double averageMark = rs.getDouble("average_mark");
        boolean blocked = rs.getBoolean("blocked");
        boolean admin = rs.getBoolean("admin");
        return new User(String.valueOf(id),
                email,
                firstName,
                middleName,
                lastName,
                district,
                city,
                school,
                averageMark,
                blocked,
                admin);
    }

    public boolean authenticate(String email, String encryptedPassword) {
        boolean result = false;
        String query = "SELECT * FROM user WHERE email = ? AND password = ? AND blocked IS FALSE";
        try (Connection connection = getConnection();
             PreparedStatement ps = prepare(connection, query)) {
            ps.setString(1, email);
            ps.setString(2, encryptedPassword);
            ResultSet rs = ps.executeQuery();
            result = rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public User indentificate(String email) {
        User user = null;
        String query = "SELECT * FROM user WHERE email = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = prepare(connection, query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            rs.next();
            user = buildUser(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> getAll(String rootPath) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user WHERE admin IS FALSE ORDER BY last_name, first_name";
        try (Connection connection = getConnection();
             PreparedStatement ps = prepare(connection, query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = buildUser(rs);
                user.setWithScan(new File(rootPath + UPLOAD_DIRECTORY + File.separator + user.getId() + ".jpg").exists());
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean userExists(String email) {
        boolean result = false;
        String query = "SELECT * FROM user WHERE email = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = prepare(connection, query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public User get(String id) {
        User user = null;
        String query = "SELECT * FROM user WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = prepare(connection, query)) {
            ps.setInt(1, Integer.valueOf(id));
            ResultSet rs = ps.executeQuery();
            rs.next();
            user = buildUser(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void insertStudent(Connection connection, User student, String password) {
        String query = "INSERT INTO user (email, " +
                "password, " +
                "first_name, " +
                "middle_name, " +
                "last_name, " +
                "district, " +
                "city, " +
                "school, " +
                "average_mark, " +
                "blocked, " +
                "admin) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = prepareInsert(connection, query)) {
            int i = 1;
            ps.setString(i++, student.getEmail());
            ps.setString(i++, password);
            ps.setString(i++, student.getFirstName());
            ps.setString(i++, student.getMiddleName());
            ps.setString(i++, student.getLastName());
            ps.setString(i++, student.getDistrict());
            ps.setString(i++, student.getCity());
            ps.setString(i++, student.getSchool());
            ps.setDouble(i++, student.getAverageMark());
            ps.setBoolean(i++, student.isBlocked());
            ps.setBoolean(i++, student.isAdmin());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            student.setId(String.valueOf(generatedKeys.getInt(1)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void blockUnblockStudent(String id) {
        String query = "UPDATE user SET blocked = !blocked WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = prepare(connection, query)) {
            ps.setInt(1, Integer.valueOf(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialAdminPasswordSetting(String encryptedInitialPassword) {
        String query = "UPDATE user SET password = ? WHERE email = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement ps = prepare(connection, query)) {
            ps.setString(1, encryptedInitialPassword);
            ps.setString(2, "admin@admin.com");
            ps.setString(3, "admin");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertStudentWithMarks(User student, String encryptedPassword, List<Mark> marks) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            insertStudent(connection, student, encryptedPassword);
            StudentMarkDAO.getInstance().insertStudentMarks(connection, student.getId(), marks);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
