package com.crudapp.dbfactory;

import com.crudapp.dbconnections.DBConnection;
import com.crudapp.dbconnections.MySqlDBConnection;

public class MySqlConnectionFactory implements DatabaseConnectionFactory{
    @Override
    public DBConnection getConnection() {
        return new MySqlDBConnection();
    }
}
