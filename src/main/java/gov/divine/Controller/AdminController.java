package gov.divine.Controller;

import gov.divine.Model.User;
import gov.divine.Service.MessageServiceImpl;
import gov.divine.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@Scope("session")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageServiceImpl messageService;

    @PostMapping("/registration")
    public ModelAndView registrerUser(@Valid User user, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("registration");
		User expectedUser = userService.findUserByLogin(user.getLogin());
		if (expectedUser != null) {
			bindingResult
					.rejectValue("login", "error.login",
							"Користувач з таким логіном вже існує");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "Користувач успішно зареєстровано");
			modelAndView.addObject("user", new User());
		}
		return modelAndView;
    }

    @GetMapping("/main")
    public ModelAndView exchangerPage(){
        ModelAndView model = new ModelAndView("main");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByLogin(auth.getName());
        model.addObject("userName", user.getSubvision() + " - (" + user.getSurname() + " " + user.getName()+")");
        model.addObject("currentUserId", user.getId());
        return model;
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView loginPage(){
        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        return model;
    }

    @GetMapping("/registration")
    public ModelAndView defaultPage(){
        ModelAndView model = new ModelAndView();
        User user = new User();
        model.addObject("user", user);
        model.setViewName("registration");
        return model;
    }

    @GetMapping("/error")
    public ModelAndView errorPage(){
        ModelAndView model = new ModelAndView();
        model.setViewName("error");
        return model;
    }
}
