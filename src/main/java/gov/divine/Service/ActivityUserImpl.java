package gov.divine.Service;

import gov.divine.Model.User;
import gov.divine.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("activityUserImpl")
@Scope("request")
public class ActivityUserImpl implements ActivityUser {
    private User currentUser;

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(i -> !i.equals(getCurrentUser()))
                .sorted(Comparator.comparing(User::getSubvision)
                .thenComparing(User::getSurname)
                .thenComparing(User::getName))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getActiveUsers() {
        return sessionRegistry.getAllPrincipals().stream()
                .map(UserDetails.class::cast)
                .map(UserDetails::getUsername)
                .filter(i -> !i.equals(getCurrentUser().getLogin()))
                .map(j -> userRepository.findByLogin(j))
                .sorted(Comparator.comparing(User::getSubvision)
                .thenComparing(User::getSurname)
                .thenComparing(User::getName))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getSubscribers() {
        return getCurrentUser().getSubscribers().stream()
                .sorted(Comparator.comparing(User::getSubvision)
                .thenComparing(User::getSurname)
                .thenComparing(User::getName))
                .collect(Collectors.toList());
    }

    @Override
    public void subscribeOn(User user) {
        User currentUser = getCurrentUser();
        if (currentUser.getSubscribers().add(user)){
            userRepository.save(currentUser);
        }
    }

    @Override
    public void subscribeOff(User user) {
        User currentUser = getCurrentUser();
        if (currentUser.getSubscribers().remove(user)){
            userRepository.save(currentUser);
        }
    }

    @Override
    public Map<String, Boolean> isSubscriber(Long id) {
        Optional<User> optional = getCurrentUser().getSubscribers().stream().filter(i -> i.getId() == id).findFirst();
        if (optional.isPresent()) return Collections.singletonMap("isSubscriber", true);
        else {
          return Collections.singletonMap("isSubscriber", false);
        }
    }

    private User getCurrentUser(){
        if (currentUser == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            currentUser = userRepository.findByLogin(auth.getName());
        }
        return currentUser;
    }

}
