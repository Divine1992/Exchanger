package gov.divine.Controller;

import gov.divine.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/exchanger")
    public String exchangerPage(){
        return "exchanger";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/")
    public String defaultPage(){
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(){
        return "registration";
    }
}
