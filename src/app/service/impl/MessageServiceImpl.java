package app.service.impl;

import app.dao.MessageRepository;
import app.exeption.InvalidEntityDataException;
import app.model.Message;
import app.model.MessagesGroupedByUser;
import app.service.MessageService;

import java.util.Collection;

public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepo;

    public MessageServiceImpl(MessageRepository messageRepo) {
        this.messageRepo = messageRepo;
    }

    @Override
    public Message create(Message message) throws InvalidEntityDataException {
        if (message.getMessage().length() > 250) {
            throw new InvalidEntityDataException("Message is too long");
        }
        return messageRepo.create(message);
    }

    @Override
    public Collection<MessagesGroupedByUser> messagesGroupedForUser(Long provider_id, Long user_id) {
        return  messageRepo.messagesGroupedForUser(provider_id, user_id);
    }

    @Override
    public Collection<MessagesGroupedByUser> messagesGroupedForProvider(Long provider_id, Long user_id) {
        return messageRepo.messagesGroupedForProvider(provider_id, user_id);
    }

    @Override
    public Collection<Message> messagesChat(Long user_id, Long service_provider_id) {
        return messageRepo.messagesChat(user_id, service_provider_id);
    }

    @Override
    public Long countMessagesForUsers(Long user_id, Long service_provider_id) {
        return messageRepo.countMessagesForUsers(user_id, service_provider_id);
    }
}
