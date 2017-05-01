package gov.divine.Controller;

import gov.divine.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    private MessageRepository messageRepository;

    @Autowired
    public ViewController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/exchanger")
    public String exchangerPage(){
        return "exchanger";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
