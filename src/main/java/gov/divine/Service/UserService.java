package gov.divine.Service;

import gov.divine.Model.User;

import java.util.List;

public interface UserService {
    User findUserByLogin(String login);
    void saveUser(User user);
    List<String> getActiveUsers();
    List<String> getAllUsers();
}
