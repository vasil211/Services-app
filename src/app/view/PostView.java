package app.view;

import app.model.Category;
import app.model.Post;
import app.model.Rating;
import app.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

public class PostView {
    Scanner sc = new Scanner(System.in);


    public Post createPost(User user, ArrayList<Category> categories) {

        String idStr;
        int id;
        System.out.println("Choose category: ");
        AtomicInteger i = new AtomicInteger(1);
        categories.forEach(category -> {
            System.out.println(i.getAndIncrement() + ": " + category.getName());
        });
        do {
            System.out.println("Enter the number of category to put the post in: ");
            idStr = sc.nextLine();
            id = Integer.parseInt(idStr);
            if (id < 1 || id > categories.size()) {
                System.out.println("Invalid input");
                continue;
            }
            System.out.println("You will put the post in category: " + categories.get(id - 1).getName());
            System.out.println("yes/no ?");
            String confirm = sc.nextLine();
            if (confirm.equals("yes")) {
                break;
            }
        } while (true);
        String title;
        do {
            System.out.println("Enter name: ");
            title = sc.nextLine();
            if (title.length() < 3) {
                System.out.println("Name too short");
            } else if (title.length() > 50) {
                System.out.println("Name too long, max 50 characters");
            }
        } while (title.length() > 50);
        String info;
        do {
            System.out.println("Enter Information: ");
            info = sc.nextLine();
            if (info.length() < 3) {
                System.out.println("Info too short");
            } else if (info.length() > 250) {
                System.out.println("Information too long, max 50 characters");
            }
        } while (info.length() > 250);
        Post post = new Post();
        post.setUser(user);
        post.setCategory(categories.get(id - 1));
        post.setName(title);
        post.setInfo(info);
        post.setCreated(LocalDateTime.now());
        post.setModified(LocalDateTime.now());
        return post;
    }

    public String updateName(Post post) {
        String name = "";
        do {
            System.out.println("Enter name: ");
            name = sc.nextLine();
            if (name.length() < 3) {
                System.out.println("Name too short");
            } else if (name.length() > 50) {
                System.out.println("Name too long, max 50 characters");
            }
        } while (name.length() > 50);
        return name;
    }

    public String updateInformation(Post post) {
        String info;
        do {
            System.out.println("Enter Information: ");
            info = sc.nextLine();
            if (info.length() < 3) {
                System.out.println("Info too short");
            } else if (info.length() > 250) {
                System.out.println("Information too long, max 50 characters");
            }
        } while (info.length() > 250);
        return info;
    }

    public Category updateCategory(Post post, ArrayList<Category> categories) {
        String idStr;
        int id;
        System.out.println("Choose category: ");
        AtomicInteger i = new AtomicInteger(1);
        categories.forEach(category -> {
            System.out.println(i.getAndIncrement() + ": " + category.getName());
        });
        do {
            System.out.println("Enter the number of category to put the post in: ");
            idStr = sc.nextLine();
            id = Integer.parseInt(idStr);
            if (id < 1 || id > categories.size()) {
                System.out.println("Invalid input");
                continue;
            }
            System.out.println("You will put the post in category: " + categories.get(id - 1).getName());
            System.out.println("yes/no ?");
            String confirm = sc.nextLine();
            if (confirm.equals("yes")) {
                break;
            }
        } while (true);
        return categories.get(id - 1);
    }

    public void displayPost(Post post) {
        StringJoiner sj = new StringJoiner(" ", "\nPost ", "");
        sj.add("from: " + post.getUser().getFirstName() + " " + post.getUser().getLastName());
        sj.add("\nCategory: " + post.getCategory().getName());
        sj.add("\nTitle: " + post.getName());
        sj.add("\nInfo: " + post.getInfo());
        sj.add("\nLast modified: " + post.getModified().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        System.out.println(sj);
    }

}
