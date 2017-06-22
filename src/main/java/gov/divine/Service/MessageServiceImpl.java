package gov.divine.Service;

import gov.divine.Model.Message;
import gov.divine.Model.User;
import gov.divine.Repository.MessageRepository;
import gov.divine.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("messageService")
@Scope("session")
public class MessageServiceImpl implements MessageService {
    private String filePath = System.getProperty("user.dir")+ File.separator+
                                "src"+File.separator+
                                "main"+File.separator+
                                "resources"+File.separator+
                                "downloads";
    @Autowired
    @Qualifier("messageRepository")
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Override
    public boolean save(Message message) {
        return messageRepository.save(message) != null;
    }

    @Override
    public List<List<Message>> sendMessages(Long userSenderId) {
        List<Message> myMessages = messageRepository.findAll().stream()
                .filter(i -> i.getSenderUserId() == userSenderId).sorted(Comparator.comparing(Message::getSendDate).reversed()).collect(Collectors.toList());
        return separateList(myMessages);
    }

    @Override
    public List<List<Message>> recieveMessages(Long userReceiverId) {
        List<Message> myMessages = messageRepository.findAll().stream()
                .filter(i -> i.getReceiverUserId() == userReceiverId).sorted(Comparator.comparing(Message::getSendDate).reversed()).collect(Collectors.toList());
        return separateList(myMessages);
    }

    @Override
    public List<List<Message>> delete(Long id) {
        Message message = messageRepository.findOne(id);
        if (message.getFile() != null || !Objects.equals(message.getFile(), "")){
            File file = new File(filePath+File.separator+message.getFile());
            file.delete();
        }
        messageRepository.delete(id);
        return sendMessages(getCurrentUser().getId());
    }

    public List<List<Message>> separateList(List<Message> allMessages){
        List<List<Message>> resultList = new ArrayList<>();
        List<Message> addList = new ArrayList<>(11);
        allMessages.stream().forEach(i -> {
            addList.add(i);
            if (addList.size() > 10 || allMessages.indexOf(i) == allMessages.size()-1){
                resultList.add(new ArrayList<>(addList));
                addList.clear();
            }
        });
        return resultList;
    }

    private User getCurrentUser(){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return userRepository.findByLogin(auth.getName());
     }

}
