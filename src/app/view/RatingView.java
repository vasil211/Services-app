package app.view;

import app.model.Post;
import app.model.Rating;
import app.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.StringJoiner;

public class RatingView {
    public void displayRating(Rating rating) {
        StringJoiner sj = new StringJoiner("", "\n Comment ", "");
        sj.add("from: " + rating.getUser().getFirstName() + " " + rating.getUser().getLastName());
        sj.add("\n Rating: " + rating.getRating());
        sj.add("\n Comment: " + rating.getComment());
        sj.add("\n Date: " + rating.getModified().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        System.out.println(sj.toString());
    }

    public Rating updateReview(Rating rating) {
        Rating updated = new Rating();
        Scanner sc = new Scanner(System.in);
        System.out.println("\nOld comment: \n" + rating.getComment());
        String comment = "";
        do {
            System.out.println("\nEnter new comment: ");
            comment = sc.nextLine();
            if (comment.length() > 250) {
                System.out.println("Comment must be less than 250 characters");
                continue;
            }
            break;
        } while (true);
        updated.setComment(comment);
        System.out.println("\nOld Rating: " + rating.getRating());
        float rate = 0;
        do {
            System.out.println("\nEnter new rating: ");
            comment = sc.nextLine();
            try {
                rate = Float.parseFloat(comment);
                if (rate < 1 || rate > 5) {
                    System.out.println("Rating must be between 1 and 5");
                    continue;
                }
                break;
            }catch (NumberFormatException e){
                System.out.println("Invalid input");
            }
        } while (true);
        updated.setRating(rate);
        updated.setModified(LocalDateTime.now());
        updated.setId(rating.getId());
        return updated;
    }

    public Rating createReview(Post post, User user) {
        Rating created = new Rating();
        Scanner sc = new Scanner(System.in);
        String comment = "";
        do {
            System.out.println("\nEnter comment: ");
            comment = sc.nextLine();
            if (comment.length() > 250) {
                System.out.println("Comment must be less than 250 characters");
                continue;
            }
            break;
        } while (true);
        created.setComment(comment);
        float rate = 0;
        do {
            System.out.println("\nEnter rating: ");
            comment = sc.nextLine();
            try {
                rate = Float.parseFloat(comment);
                if (rate < 1 || rate > 5) {
                    System.out.println("Rating must be between 1 and 5");
                    continue;
                }
                break;
            }catch (NumberFormatException e){
                System.out.println("Invalid input");
            }
        } while (true);
        created.setRating(rate);
        created.setUserProvider(post.getUser());
        created.setModified(LocalDateTime.now());
        created.setPost(post);
        created.setUser(user);
        created.setCreated(LocalDateTime.now());
        return created;
    }
}
