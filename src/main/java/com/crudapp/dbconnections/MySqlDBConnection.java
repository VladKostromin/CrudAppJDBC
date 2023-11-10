package com.crudapp.dbconnections;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlDBConnection implements DBConnection {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/sys";
    private static final String USER = "root";
    private static final String PASSWORD = "qaz923923";

    private Connection connection = null;

    @SneakyThrows
    public Connection createConnection() {
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        return connection;
    }
}
