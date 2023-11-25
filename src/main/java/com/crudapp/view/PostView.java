package com.crudapp.view;

import com.crudapp.controller.PostController;
import com.crudapp.exceptions.NotFoundException;
import com.crudapp.exceptions.StatusDeletedException;
import com.crudapp.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostView {

    private final Scanner scanner;
    private final PostController postController;

    public PostView(PostController postController) {
        this.postController = postController;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean flag = true;
        int inputOption;

        while (flag) {
            System.out.println("1. Создать новый пост");
            System.out.println("2. Получить пост");
            System.out.println("3. Обновить существующий пост");
            System.out.println("4. Добавить лейбл к посту");
            System.out.println("5. Получить все посты");
            System.out.println("6. Удалить пост");
            System.out.println("7. Вернутся в меню программы");

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
                    System.out.println("Введите контент поста:");
                    String content = scanner.nextLine();
                    try {
                        postController.createNewPost(content, writerId);
                    } catch (NotFoundException e) {
                        System.out.println(e.getMessage());
                    }
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
                        postForUpdate = postController.updatePostContent(contentForUpdate, postIdForUpdate);
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
                    System.out.print("Введите id поста для добавления Label: ");
                    Long postIdForLabel = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("Введите id Label: ");
                    Long labelId = scanner.nextLong();
                    postController.addLabelToPost(postIdForLabel, labelId);
                    break;
                case 5 :
                    System.out.println("Список всех постов: ");
                    List<Post> posts = new ArrayList<>(postController.getAllPosts());
                    for (Post p : posts) {
                        System.out.println(p);
                    }
                    break;
                case 6 :
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
                case 7 :
                    flag = false;
                    break;
            }
        }
    }
}
