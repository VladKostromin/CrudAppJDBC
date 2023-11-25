package com.crudapp.view;

import com.crudapp.controller.WriterController;
import com.crudapp.exceptions.NotFoundException;
import com.crudapp.model.Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriterView {
    private final Scanner scanner;
    private final WriterController writerController;

    public WriterView(WriterController writerController) {
        this.scanner = new Scanner(System.in);
        this.writerController = writerController;
    }

    public void run() {
        boolean flag = true;
        int inputOption;
        while (flag) {
            System.out.println("1. Создать нового Writer");
            System.out.println("2. Выбрать Writer");
            System.out.println("3. Обновить Writer");
            System.out.println("4. Получить всех Writer");
            System.out.println("5. Удалить Writer");
            System.out.println("6. Вернутся в меню программы");
            inputOption = scanner.nextInt();
            scanner.nextLine();
            switch (inputOption) {
                case 1 :
                    System.out.print("Введите имя: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Введите фамилиюю: ");
                    String lastName = scanner.nextLine();
                    writerController.createNewWriter(firstName, lastName);
                    System.out.println("Writer успешно создан!");
                    break;
                case 2 :
                    System.out.print("Введите Writer id: ");
                    Long writerId;
                    try {
                        writerId = scanner.nextLong();
                    } catch (Exception e) {
                        System.out.println("Неверный ввод");
                        break;
                    }
                    Writer writer;
                    try {
                        writer = writerController.getWriter(writerId);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    System.out.println("Writer получен: ");
                    System.out.println(writer);
                    break;
                case 3 :
                    Writer writerForUpdate;
                    System.out.print("Введите id для обновления: ");
                    Long writerIdForUpdate;
                    try {
                        writerIdForUpdate = scanner.nextLong();
                    } catch (Exception e) {
                        System.out.println("Неверный ввод");
                        break;
                    }
                    scanner.nextLine();
                    System.out.print("Введите имя для обновления: " );
                    String nameForUpdate = scanner.nextLine();
                    System.out.print("Введите фамилию для обновления: ");
                    String lastNameForUpdate = scanner.nextLine();
                    try {
                        writerForUpdate = writerController.updateWriter(nameForUpdate, lastNameForUpdate, writerIdForUpdate);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    System.out.println("Обновленный Writer:");
                    System.out.println(writerForUpdate);
                    break;
                case 4 :
                    System.out.println("Список всех Writer's:");
                    List<Writer> writerList = new ArrayList<>(writerController.getAllWriters());
                    for (Writer w : writerList) {
                        System.out.println(w);
                    }
                    break;
                case 5 :
                    System.out.print("Введите id для удаления: ");
                    Long writerIdForDelete;
                    try {
                        writerIdForDelete = scanner.nextLong();
                    } catch (Exception e) {
                        System.out.println("Неверный ввод");
                        break;
                    }
                    scanner.nextLine();
                    System.out.println("Вы уверенны?(Y/N):");
                    String confirmation = scanner.nextLine();
                    if(confirmation.equalsIgnoreCase("y")) {
                        writerController.deleteWriter(writerIdForDelete);
                        System.out.println("Writer удален");
                    } else if (confirmation.equalsIgnoreCase("n")) {
                        System.out.println("Отмена");
                        break;
                    } else {
                        System.out.println("Неверный ввод!");
                        break;
                    }
                    break;
                case 6 :
                    flag = false;
                    break;
            }
        }
    }
}
