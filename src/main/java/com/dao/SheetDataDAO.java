package com.dao;

import com.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class SheetDataDAO extends CommonDAO {
    private static SheetDataDAO instance;

    private SheetDataDAO() {

    }

    public static synchronized SheetDataDAO getInstance() {
        if (instance == null) {
            instance = new SheetDataDAO();
        }
        return instance;
    }

    public void insertSheetData(Connection connection, int sheetId) {
        String query = "INSERT INTO sheet_data (sheet_id, faculty_id, student_id, faculty_exam_id, mark) " +
                "SELECT ?, r.faculty_id, r.student_id, e.faculty_exam_id, e.mark FROM exam_mark e " +
                "INNER JOIN registration r ON r.id = e.registration_id " +
                "ORDER BY r.id";
        try (PreparedStatement ps = prepare(connection, query)) {
            ps.setInt(1, sheetId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getLastSheet(Connection connection,
                             NavigableMap<String, NavigableSet<User>> budgetMap,
                             NavigableMap<String, NavigableSet<User>> contractMap) {
        String query = ("SELECT sd.faculty_id, " +
                "SUM(sd.mark) AS total_mark, " +
                "s.average_mark, " +
                "sd.student_id, " +
                "f.common, " +
                "f.budget, " +
                "f.name, " +
                "s.first_name, " +
                "s.middle_name, " +
                "s.last_name, " +
                "s.email " +
                "FROM sheet_data sd " +
                "INNER JOIN user s ON s.id = sd.student_id " +
                "INNER JOIN faculty f ON f.id = sd.faculty_id " +
                "WHERE s.blocked = FALSE AND sd.sheet_id = (SELECT MAX(id) FROM sheet) " +
                "GROUP BY sd.faculty_id, " +
                "s.average_mark, " +
                "sd.student_id, " +
                "f.common, " +
                "f.budget, " +
                "f.name, " +
                "s.first_name, " +
                "s.middle_name, " +
                "s.last_name, " +
                "s.email " +
                "ORDER BY faculty_id, total_mark DESC, average_mark DESC, sd.id");
        try (PreparedStatement ps = prepare(connection, query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String faculty = rs.getString("name");
                NavigableSet<User> budgetSubset = budgetMap.computeIfAbsent(faculty, k -> new TreeSet<>());
                NavigableSet<User> contractSubset = contractMap.computeIfAbsent(faculty, k -> new TreeSet<>());
                User student = new User(null,
                        rs.getString("email"),
                        rs.getString("first_name"),
                        rs.getString("middle_name"),
                        rs.getString("last_name"),
                        null,
                        null,
                        null,
                        0,
                        false,
                        false);
                int budget = rs.getInt("budget");
                int contract = rs.getInt("common") - budget;
                if (budgetSubset.size() < budget) {
                    budgetSubset.add(student);
                } else if (contractSubset.size() < contract) {
                    contractSubset.add(student);
                }
            }
            Set<String> emptyFaculties = budgetMap
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue().isEmpty())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
            emptyFaculties.forEach(budgetMap::remove);
            emptyFaculties = contractMap
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue().isEmpty())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
            emptyFaculties.forEach(contractMap::remove);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
