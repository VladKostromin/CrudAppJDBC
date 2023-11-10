package com.crudapp.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class ApplicationView {
    private final Connection connection;
    private final Scanner scanner;



    public ApplicationView(Connection connection) {
        this.connection = connection;
        this.scanner = new Scanner(System.in);
    }

    public void init() {
        int inputOption;
        WriterView writerView = new WriterView(connection);
        PostView postView = new PostView(connection);
        LabelView labelView = new LabelView(connection);
        boolean flag = true;
        System.out.println("Добро подаловать в приложение CRUD");
        while (flag) {
            System.out.println("Выберите опцию:");
            System.out.println("1. Writer меню");
            System.out.println("2. Post меню");
            System.out.println("3. Label меню");
            System.out.println("4. Выход из программы");
            inputOption = scanner.nextInt();
            scanner.nextLine();
            switch (inputOption) {
                case 1 :
                    writerView.run();
                    break;
                case 2 :
                    postView.run();
                    break;
                case 3 :
                    labelView.run();
                    break;
                case 4 :
                    flag = false;
                    break;
                default:
                    System.out.println("Неверный ввод");
            }
        }
        System.out.println("Программа завершается");
    }

}
