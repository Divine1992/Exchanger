package gov.divine.Service;

import gov.divine.Model.User;

import java.util.List;
import java.util.Set;

public interface ActivityUser {
    List<User> getAllUsers();
    List<User> getActiveUsers();
    Set<User> getSubscribers();
    void subscribe(User user);
}
