package gov.divine.Service;

import gov.divine.Model.User;

import java.util.List;
import java.util.Map;

public interface ActivityUser {
    List<User> getAllUsers();
    List<User> getActiveUsers();
    List<User> getSubscribers();
    void subscribeOn(User user);
    void subscribeOff(User user);
    Map<String, Boolean> isSubscriber(Long id);
}
