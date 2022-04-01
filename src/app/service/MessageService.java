package app.service;

import app.exeption.InvalidEntityDataException;
import app.model.Message;
import app.model.MessagesGroupedForUser;

import java.util.Collection;

public interface MessageService {
    Message create(Message message) throws InvalidEntityDataException;
    Collection<MessagesGroupedForUser> messagesGroupedForUser(Long user_id);
    Collection<MessagesGroupedForUser> messagesGroupedForProvider(Long user_id);
    Collection<Message> messagesChat(Long user_id, Long service_provider_id);
    Long countMessagesForUsers(Long user_id, Long service_provider_id);
}
