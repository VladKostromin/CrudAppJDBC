package com.crudapp.view;


import com.crudapp.controller.LabelController;
import com.crudapp.controller.PostController;
import com.crudapp.controller.WriterController;
import com.crudapp.services.LabelService;
import com.crudapp.services.PostService;
import com.crudapp.services.WriterService;

import java.util.Scanner;

public class ApplicationView {
    private final Scanner scanner;


    private final WriterService writerService = new WriterService();
    private final PostService postService = new PostService();
    private final LabelService labelService = new LabelService();

    private final WriterController writerController = new WriterController(writerService);
    private final PostController postController = new PostController(postService);
    private final LabelController labelController = new LabelController(labelService);

    private final WriterView writerView = new WriterView(writerController);
    private final PostView postView = new PostView(postController);
    private final LabelView labelView = new LabelView(labelController);

    public ApplicationView() {
        this.scanner = new Scanner(System.in);
    }

    public void init() {
        int inputOption;

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
