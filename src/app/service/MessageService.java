package app.service;

import app.exeption.InvalidEntityDataException;
import app.model.Message;
import app.model.MessagesGroupedByUser;

import java.util.Collection;

public interface MessageService {
    Message create(Message message) throws InvalidEntityDataException;
    Collection<MessagesGroupedByUser> messagesGroupedForUser(Long provider_id, Long user_id);
    Collection<MessagesGroupedByUser> messagesGroupedForProvider(Long provider_id, Long user_id);
    Collection<Message> messagesChat(Long user_id, Long service_provider_id);
    Long countMessagesForUsers(Long user_id, Long service_provider_id);
}
