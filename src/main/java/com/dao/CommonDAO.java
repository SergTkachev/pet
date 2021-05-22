package com.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public abstract class CommonDAO {
    private static ComboPooledDataSource connectionPool;

    private ComboPooledDataSource getConnectionPool() {
        if (connectionPool == null) {
            try {
                connectionPool = new ComboPooledDataSource();
                ResourceBundle properties = ResourceBundle.getBundle(("liquibase"));
                connectionPool.setDriverClass(properties.getString("driver"));
                connectionPool.setJdbcUrl(properties.getString("url"));
                connectionPool.setUser(properties.getString("username"));
                connectionPool.setPassword(properties.getString("password"));
                connectionPool.setMinPoolSize(10);
                connectionPool.setAcquireIncrement(1);
                connectionPool.setMaxPoolSize(100);
            } catch (PropertyVetoException ex) {
                ex.printStackTrace();
            }
        }
        return connectionPool;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = getConnectionPool().getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    protected PreparedStatement prepare(Connection connection, String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    protected PreparedStatement prepareInsert(Connection connection, String sql) throws SQLException {
        return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }
}
