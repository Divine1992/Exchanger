package gov.divine.Controller;

import gov.divine.Model.User;
import gov.divine.Repository.UserRepository;
import gov.divine.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public User registerUser(@RequestBody User user){
        User expectedUser = userService.findUserByLogin(user.getLogin());
        if (expectedUser != null){
            return null;
        }
        userService.saveUser(user);
        return user;
    }

}
