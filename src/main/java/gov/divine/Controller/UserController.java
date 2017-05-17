package gov.divine.Controller;

import gov.divine.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exchanger/main")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUsers")
    public List<String> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/getActiveUsers")
    public List<String> getAllActiveUsers(){
        return userService.getActiveUsers();
    }

}
