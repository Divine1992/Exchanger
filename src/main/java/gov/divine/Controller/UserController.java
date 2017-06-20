package gov.divine.Controller;

import gov.divine.Model.User;
import gov.divine.Service.ActivityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
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
    public List<User> getSubscribers(){
        return activityUser.getSubscribers();
    }

    @PostMapping("/subscribeOn")
    public void subscibeOn(@RequestBody @Valid User user){
        activityUser.subscribeOn(user);
    }

    @PostMapping("/subscribeOff")
    public void subscibeOff(@RequestBody @Valid User user){
        activityUser.subscribeOff(user);
    }

    @GetMapping("/isSubscriber/{id}")
    @ResponseBody
    public Map<String, Boolean> isSubscriber(@PathVariable("id") Long id){
        return activityUser.isSubscriber(id);
    }

}
