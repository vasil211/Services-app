package app.dao;

import app.model.Category;
import app.model.Message;
import app.model.MessagesGroupedByUser;

import java.util.Collection;

public interface MessageRepository extends Repository<Long, Message>{
    Collection<MessagesGroupedByUser> messagesGroupedForUser(Long provider_id, Long user_id);
    Collection<MessagesGroupedByUser> messagesGroupedForProvider(Long provider_id, Long user_id);
    Collection<Message> messagesChat(Long provider_id, Long user_id);
    Long countMessagesForUsers(Long user_id, Long service_provider_id);
}
