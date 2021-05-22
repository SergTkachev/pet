package com.dao;

import com.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NavigableMap;
import java.util.NavigableSet;

public class SheetDAO extends CommonDAO {
    private static SheetDAO instance;

    private SheetDAO() {

    }

    public static synchronized SheetDAO getInstance() {
        if (instance == null) {
            instance = new SheetDAO();
        }
        return instance;
    }

    public int insertSheet(Connection connection) {
        int sheetId = -1;
        String query = "INSERT INTO sheet () VALUES ()";
        try (PreparedStatement ps = prepareInsert(connection, query)) {
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            sheetId = generatedKeys.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sheetId;
    }

    public void createSheet(NavigableMap<String, NavigableSet<User>> budgetMap,
                            NavigableMap<String, NavigableSet<User>> contractMap) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            int sheetId = insertSheet(connection);
            SheetDataDAO.getInstance().insertSheetData(connection, sheetId);
            RegistrationDAO.getInstance().deleteRegistrations(connection);
            SheetDataDAO.getInstance().getLastSheet(connection, budgetMap, contractMap);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
