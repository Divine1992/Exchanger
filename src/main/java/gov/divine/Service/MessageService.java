package gov.divine.Service;

import gov.divine.Model.Message;

import java.util.List;

public interface MessageService {
    boolean save(Message message);
    List<List<Message>> sendMessages(Long senderUserId);
    List<List<Message>> recieveMessages(Long receiverUserSId);
    List<List<Message>> delete(Long id);
}
