package app.view;

import app.model.Category;
import app.model.Post;
import app.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class PostView {

    public Post createPost(User user, ArrayList<Category> categories) {
        Scanner sc = new Scanner(System.in);
        String idStr;
        int id;
        System.out.println("Choose category: ");
        AtomicInteger i = new AtomicInteger(1);
        categories.forEach(category -> {
            System.out.println(i.getAndIncrement() + ": " + category.getName());
        });
        do {
            System.out.println("Enter number of category to put the post in: ");
            idStr = sc.nextLine();
            id = Integer.parseInt(idStr);
            if(id < 1 || id > categories.size()){
                System.out.println("Invalid input");
                continue;
            }
            System.out.println("You will put the post in category: " + categories.get(id - 1).getName());
            System.out.println("yes/no ?");
            String confirm = sc.nextLine();
            if (confirm.equals("yes")) {
                break;
            }
        }while (true);
        String title;
        do {
            System.out.println("Enter title: ");
            title = sc.nextLine();
            if(title.length() < 3){
                System.out.println("Title too short");
            }else if(title.length() > 50){
                System.out.println("Title too long, max 50 characters");
            }
        }while (title.length() > 50);
        String info;
        do {
            System.out.println("Enter Information: ");
            info = sc.nextLine();
            if(info.length() < 3){
                System.out.println("Info too short");
            }else if(info.length() > 250){
                System.out.println("Information too long, max 50 characters");
            }
        }while (info.length() > 250);
        Post post = new Post();
        post.setUser(user);
        post.setCategory(categories.get(id - 1));
        post.setName(title);
        post.setInfo(info);
        post.setCreated(LocalDateTime.now());
        post.setModified(LocalDateTime.now());
        return post;
    }
    public Post updatePost(Post post){
        // TODO: implement
        return post;
    }
}
