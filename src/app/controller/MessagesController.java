package app.controller;

import app.exeption.NonexistingEntityException;
import app.model.*;
import app.service.MessageService;
import app.service.UserService;
import app.view.Menu;
import app.view.MessagesView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;

public class MessagesController {
    private final MessagesView messagesView;
    private final MessageService messageService;


    public MessagesController(MessagesView messagesView, MessageService messageService) {
        this.messagesView = messagesView;
        this.messageService = messageService;

    }

    public boolean init(User user, ArrayList<MessagesGroupedForUser> messages) {
        Scanner sc = new Scanner(System.in);
        String input;
        int id;
        do {
            System.out.println("Chose chat to open <1 - " + messages.size() + ">");
            input = sc.nextLine();
            id = Integer.parseInt(input);
            if (id < 1 || id > messages.size()) System.out.println("Invalid input");
            else break;
        } while (true);
        // todo menu
        if (user.getRole().equals(Role.SERVICE_PROVIDER)) {
            do {
                var firstMessage = messagesView.chatForProvider(user,
                        messages.get(messages.size() - id).getUserId());
                var menu = new Menu("", List.of(
                        new Menu.Option("Send message", () -> {
                            String message = "";
                            do {
                                System.out.println("Enter message");
                                message = sc.nextLine();
                                if (message.length() > 250) System.out.println("Message is too long");
                            } while (message.length() > 250);
                            Message newMessage = new Message();
                            newMessage.setSender(user.getId());
                            newMessage.setUserProvider(user);
                            newMessage.setUser(firstMessage.getUser());
                            newMessage.setPost(firstMessage.getPost());
                            newMessage.setMessage(message);
                            newMessage.setSent(LocalDateTime.now());
                            messageService.create(newMessage);
                            return "";
                        })
                ));
                var check = menu.showForForEach();
                if (check) break;
            } while (true);
        } else if (user.getRole().equals(Role.USER)) {
            do {
                var firstMessage = messagesView.chatForUser(user,
                        messages.get(messages.size() - id).getService_providerId());

                var menu = new Menu("", List.of(
                        new Menu.Option("Send message", () -> {
                            String message = "";
                            do {
                                System.out.println("Enter message");
                                message = sc.nextLine();
                                if (message.length() > 250) System.out.println("Message is too long");
                            } while (message.length() > 250);
                            Message newMessage = new Message();
                            newMessage.setSender(user.getId());
                            newMessage.setUserProvider(firstMessage.getUserProvider());
                            newMessage.setUser(user);
                            newMessage.setPost(firstMessage.getPost());
                            newMessage.setMessage(message);
                            newMessage.setSent(LocalDateTime.now());
                            messageService.create(newMessage);
                            return "";
                        })
                ));
                var check = menu.showForForEach();
                if (check) break;
            } while (true);
        }
        return false;
    }
}
