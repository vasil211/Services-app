package app.dao;

import app.model.Message;
import app.model.MessagesGroupedForUser;

import java.util.Collection;

public interface MessageRepository extends Repository<Long, Message>{
    Collection<MessagesGroupedForUser> messagesGroupedForUser(Long user_id);
    Collection<MessagesGroupedForUser> messagesGroupedForProvider(Long provider_id);
    Collection<Message> messagesChat(Long provider_id, Long user_id);
    Long countMessagesForUsers(Long user_id, Long service_provider_id);
}
