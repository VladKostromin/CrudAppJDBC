package com.crudapp.dbconnections;

public interface DBConnection {
    <T> T createConnection();
}
