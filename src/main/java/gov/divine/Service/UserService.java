package gov.divine.Service;

import gov.divine.Model.User;

public interface UserService {
    User findUserByLogin(String login);
    void saveUser(User user);
}
