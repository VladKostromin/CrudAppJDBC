package com.crudapp;

import com.crudapp.view.ApplicationView;

import java.sql.SQLException;

public class ApplicationRunner {

    public static void main(String[] args) throws SQLException {
        ApplicationView applicationView = new ApplicationView();
        applicationView.init();
    }
}
