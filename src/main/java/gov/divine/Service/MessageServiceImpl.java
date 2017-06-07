package gov.divine.Service;

import gov.divine.Model.Message;
import gov.divine.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("messageService")
@Scope("session")
public class MessageServiceImpl implements MessageService {
    private String userName;

    @Autowired
    @Qualifier("messageRepository")
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

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

    /*
    @Override
    public List<List<Message>> findByFromLoginOrToLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByLogin(auth.getName());
        String userName = user.getSubvision() + " - (" + user.getName() + " " + user.getSurname()+")";
        List<Message> messages = messageRepository.findByFromLoginOrToLoginOrderBySendDateDesc(userName, userName);
        List<List<Message>> resultList = new ArrayList<>();
        List<Message> list = new ArrayList<>(18);
        for (int i = 0; i < messages.size(); i++){
            list.add(messages.get(i));
            if (list.size() == 18 || i == messages.size()-1){
                List<Message> setList = new ArrayList<>(list);
                resultList.add(setList);
                list.clear();
            }
        }
        return resultList;
    }*/

}
