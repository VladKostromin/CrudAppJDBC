package com.crudapp;

import com.crudapp.dbconnections.DBConnection;
import com.crudapp.dbfactory.DatabaseConnectionFactory;
import com.crudapp.dbfactory.MySqlConnectionFactory;
import com.crudapp.view.ApplicationView;

import java.sql.Connection;
import java.sql.SQLException;

public class ApplicationRunner {
    private static final DatabaseConnectionFactory factory = new MySqlConnectionFactory();
    private static final DBConnection connection = factory.getConnection();
    private static final Connection sqlConnection = connection.createConnection();

    public static void main(String[] args) throws SQLException {
        ApplicationView applicationView = new ApplicationView(sqlConnection);
        applicationView.init();
        sqlConnection.close();

    }
}
