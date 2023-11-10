package com.crudapp.dbfactory;

import com.crudapp.dbconnections.DBConnection;

public interface DatabaseConnectionFactory {
    DBConnection getConnection();
}
