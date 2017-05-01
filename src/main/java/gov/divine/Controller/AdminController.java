package gov.divine.Controller;

import gov.divine.Model.User;
import gov.divine.Repository.UserRepository;
import gov.divine.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @PostMapping("/registration")
    public User registerUser(@RequestBody User user){
        User expectedUser = userService.findUserByLogin(user.getLogin());
        if (expectedUser != null){
            return null;
        }
        userService.saveUser(user);
        return user;
    }

    /*@PostMapping("/login")
    public String login(@RequestBody User user){
        User expectedUser = userService.findUserByLogin(user.getLogin());
        if (expectedUser.getPassword().equals(user.getPassword())){
            return "redirect: exchanger.html";
        }
        return "redirect: login.html";
    }*/

}
