package gov.divine.Service;

import gov.divine.Controller.FileUploadController;
import gov.divine.Model.Message;
import gov.divine.Model.User;
import gov.divine.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    @Qualifier("messageRepository")
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    public void save(Message message){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByLogin(auth.getName());
        String userName = user.getSubvision() + " - (" + user.getName() + " " + user.getSurname()+")";
        message.setSendDate(new Date());
        message.setFromLogin(userName);
        messageRepository.save(message);
    }

    @Override
    public List<Message> findByFromLoginOrToLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByLogin(auth.getName());
        String userName = user.getSubvision() + " - (" + user.getName() + " " + user.getSurname()+")";
        return messageRepository.findByFromLoginOrToLogin(userName, userName);
    }

    @Override
    public List<Message> findByFromLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByLogin(auth.getName());
        String userName = user.getSubvision() + " - (" + user.getName() + " " + user.getSurname()+")";
        return messageRepository.findByFromLogin(userName);
    }

    @Override
    public List<Message> findByToLogin() {
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       User user = userService.findUserByLogin(auth.getName());
       String userName = user.getSubvision() + " - (" + user.getName() + " " + user.getSurname()+")";
       return messageRepository.findByToLogin(userName);
    }
}
