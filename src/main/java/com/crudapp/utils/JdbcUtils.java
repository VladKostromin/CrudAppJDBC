package com.crudapp.utils;

import java.sql.*;

public class JdbcUtils {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/crud";
    private static final String USER = "root";
    private static final String PASSWORD = "qaz923923";

    private static Connection connection = null;


    private static Connection getConnection() {
        if(connection == null) {
            try {
                connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            } catch (Throwable e) {
                System.out.println("Unable to obtain connection");
                e.printStackTrace();
                System.exit(1);
            }
        }
        return connection;
    }
    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }

    public static PreparedStatement getPreparedStatementWithKeys(String sql) throws SQLException {
        return getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

}
