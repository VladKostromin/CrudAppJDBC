package com.crudapp.view;

import com.crudapp.controller.PostController;
import com.crudapp.controller.WriterController;
import com.crudapp.exceptions.NotFoundException;
import com.crudapp.exceptions.StatusDeletedException;
import com.crudapp.model.Label;
import com.crudapp.model.Post;
import com.crudapp.model.Writer;
import com.crudapp.repository.PostRepository;
import com.crudapp.repository.WriterRepository;
import com.crudapp.repository.jdbc.JdbcPostRepositoryImpl;
import com.crudapp.repository.jdbc.JdbcWriterRepositoryImpl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostView {
    private Connection connection;
    private final Scanner scanner;

    public PostView(Connection connection) {
        this.connection = connection;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        PostRepository postRepository = new JdbcPostRepositoryImpl(connection);
        PostController postController = new PostController(postRepository);

        WriterRepository writerRepository = new JdbcWriterRepositoryImpl(connection);
        WriterController writerController = new WriterController(writerRepository);

        boolean flag = true;
        int inputOption;

        while (flag) {
            System.out.println("1. Создать новый пост");
            System.out.println("2. Получить пост");
            System.out.println("3. Обновить существующий пост");
            System.out.println("4. Получить все посты");
            System.out.println("5. Удалить пост");
            System.out.println("6. Вернутся в меню программы");
            inputOption = scanner.nextInt();
            scanner.nextLine();
            switch (inputOption) {
                case 1 :
                    System.out.print("Введите id Writer для создания поста: ");
                    Long writerId;
                    try {
                        writerId = scanner.nextLong();
                    } catch (Exception e) {
                        System.out.println("Неверный ввод");
                        break;
                    }
                    scanner.nextLine();
                    try {
                        writerController.getWriter(writerId);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    System.out.println("Введите контент поста:");
                    String content = scanner.nextLine();
                    postController.createPost(content, writerId);
                    System.out.println("Пост создан");
                    break;
                case 2 :
                    System.out.print("Введите id поста: ");
                    Long postId;
                    try {
                        postId = scanner.nextLong();
                    } catch (Exception e) {
                        System.out.println("Неверный ввод");
                        break;
                    }
                    scanner.nextLine();
                    Post post;
                    try {
                        post = postController.getPost(postId);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                        break;
                    } catch (StatusDeletedException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    System.out.println("Пост получен:");
                    System.out.println(post);
                    break;
                case 3 :
                    Post postForUpdate;
                    System.out.print("Введите id поста для обновления: ");
                    Long postIdForUpdate;
                    try {
                        postIdForUpdate = scanner.nextLong();
                    } catch (Exception e) {
                        System.out.println("Неверный ввод");
                        break;
                    }
                    scanner.nextLine();
                    System.out.println("Введи контент для обновления: ");
                    String contentForUpdate = scanner.nextLine();
                    try {
                        postForUpdate = postController.updatePost(contentForUpdate, postIdForUpdate);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                        break;
                    } catch (StatusDeletedException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    System.out.println("Обновленный пост: ");
                    System.out.println(postForUpdate);
                    break;
                case 4 :
                    System.out.println("Список всех постов: ");
                    List<Post> posts = new ArrayList<>(postController.getAllPosts());
                    for (Post p : posts) {
                        System.out.println(p);
                    }
                    break;
                case 5 :
                    System.out.print("Введите id для удаления: ");
                    Long postIdForDelete;
                    try {
                        postIdForDelete = scanner.nextLong();
                    } catch (Exception e) {
                        System.out.println("Неверный ввод");
                        break;
                    }
                    scanner.nextLine();
                    System.out.println("Вы уверенны?(Y/N):");
                    String confirmation = scanner.nextLine();
                    if(confirmation.equalsIgnoreCase("y")) {
                        postController.deletePost(postIdForDelete);
                        System.out.println("Пост удален");
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
