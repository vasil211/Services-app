package app.view;

import app.controller.MessagesController;
import app.model.Message;
import app.model.MessagesGroupedForUser;
import app.model.Role;
import app.model.User;
import app.service.MessageService;
import app.service.UserService;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class MessagesView {
    private final MessageService messageService;

    public MessagesView(MessageService messageService) {
        this.messageService = messageService;
    }

    public void show(User user) {
        MessagesView messagesView = this;

        MessagesController messagesController = new MessagesController(messagesView, messageService);
        System.out.println("Hello, " + user.getFirstName() + " <3");
        ArrayList<MessagesGroupedForUser> messages = null;
        if (user.getRole().equals(Role.USER)) {
            messages = (ArrayList<MessagesGroupedForUser>) messageService.messagesGroupedForUser(user.getId());
        } else if (user.getRole().equals(Role.SERVICE_PROVIDER)) {
            messages = (ArrayList<MessagesGroupedForUser>) messageService.messagesGroupedForProvider(user.getId());
        }
        Collections.reverse(messages);
        if (!messages.isEmpty()) {
            boolean check = false;
            do {
                int count = messages.size();
                for (var message : messages) {
                    StringJoiner sj = new StringJoiner(" ", "\n", "");
                    sj.add("Chat " + count-- + ":");
                    sj.add(message.getFirstName() + " " + message.getLastName() + " " + message.getSent()
                            .format(DateTimeFormatter.ofPattern("HH:mm:ss  dd.MM.yy ")));
                    System.out.println(sj.toString());
                }
                check = messagesController.init(user, messages);
                System.out.println("Open another chat? (yes/no)");
                Scanner scanner = new Scanner(System.in);
                String answer = scanner.nextLine();
                if (answer.equals("yes") || answer.equals("y")) {
                    check = false;
                } else if (answer.equals("no") || answer.equals("n")) {
                    check = true;
                }
            } while (!check);
        } else {
            System.out.println("You have no messages");
        }
    }

    public Message chatForUser(User user, Long provider_id) {
        ArrayList<Message> message = (ArrayList<Message>) messageService.messagesChat(user.getId(), provider_id);
        if (!message.isEmpty()) {
            // todo make look
            message.forEach(m -> {
                if (m.getSender().equals(user.getId())) {
                    System.out.println("\nYou: \n  " + m.getMessage());
                }else if(m.getSender().equals(provider_id)){
                    System.out.println("\n" + m.getUserProvider().getFirstName() + " "
                            + m.getUserProvider().getLastName()
                            + "\n  "
                            + m.getMessage());
                }
            });
        }
        return message.get(0);
    }

    public Message chatForProvider(User provider, Long user_id) {
        ArrayList<Message> message = (ArrayList<Message>) messageService.messagesChat(user_id, provider.getId());
        if (!message.isEmpty()) {
            message.forEach(m -> {
                if (m.getSender().equals(provider.getId())) {
                    System.out.println("\nYou: \n  " + m.getMessage());
                }else if(m.getSender().equals(user_id)){
                    System.out.println("\n" + m.getUser().getFirstName() + " "
                            + m.getUser().getLastName()
                            + "\n  "
                            + m.getMessage());
                }
            });
        }
        return message.get(0);
    }
}
