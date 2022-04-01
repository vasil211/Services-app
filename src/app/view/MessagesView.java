package app.view;

import app.model.User;
import app.service.MessageService;

import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

public class MessagesView {
    private final MessageService messageService;

    public MessagesView(MessageService messageService) {
        this.messageService = messageService;
    }

    public void show(User user) {
        System.out.println("Hello, " + user.getFirstName() + "!");
        var messages = messageService.messagesGroupedForProvider(user.getId());
        if(!messages.isEmpty()) {
            AtomicInteger count = new AtomicInteger(1);
            messages.forEach(message -> {
                StringJoiner sj = new StringJoiner(" ", "\n", "");
                sj.add("Chat number: " + count.getAndIncrement());
                sj.add(message.getFirstName() + " " + message.getLastName() + message.getSent()
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
            });
            var message = messages.toArray();
            var mes = message[0];
            System.out.println(mes.toString());
            // TODO: show messages
        }else {
            System.out.println("You have no messages");
        }
    }
}
