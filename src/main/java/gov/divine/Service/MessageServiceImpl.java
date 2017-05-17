package gov.divine.Service;

import gov.divine.Model.Message;
import gov.divine.Model.User;
import gov.divine.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    @Qualifier("messageRepository")
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    public boolean save(Message message){
        if (message.getFile()== null && message.getMessage() == null){
            return false;
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByLogin(auth.getName());
        String userName = user.getSubvision() + " - (" + user.getName() + " " + user.getSurname()+")";
        message.setSendDate(new Date());
        message.setFromLogin(userName);
        messageRepository.save(message);
        return true;
    }

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
    }

    @Override
    public List<Message> findByFromLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByLogin(auth.getName());
        String userName = user.getSubvision() + " - (" + user.getName() + " " + user.getSurname()+")";
        return messageRepository.findByFromLoginOrderBySendDateDesc(userName);
    }

    @Override
    public List<Message> findByToLogin() {
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       User user = userService.findUserByLogin(auth.getName());
       String userName = user.getSubvision() + " - (" + user.getName() + " " + user.getSurname()+")";
       return messageRepository.findByToLoginOrderBySendDateDesc(userName);
    }
}
