package gov.divine.Controller;

import gov.divine.Model.User;
import gov.divine.Service.ActivityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/exchanger/main")
@Scope("request")
public class UserController {

    @Autowired
    private ActivityUser activityUser;

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return activityUser.getAllUsers();
    }

    @GetMapping("/getActiveUsers")
    public List<User> getAllActiveUsers(){
        return activityUser.getActiveUsers();
    }

    @GetMapping("/getSubscribers")
    public Set<User> getSubscribers(){
        return activityUser.getSubscribers();
    }

    @PostMapping("/subscribe")
    public void subscibe(@RequestBody @Valid User user){
        activityUser.subscribe(user);
    }

}
