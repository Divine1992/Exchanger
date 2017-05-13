package gov.divine.Repository;

import gov.divine.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("messageRepository")
public interface MessageRepository extends JpaRepository<Message, Long> {

    //@Query("select m from Message m where m.fromLogin = ?1 or m.toLogin = ?1")
    List<Message> findByFromLoginOrToLogin(String userNameFrom, String userNameTo);

    List<Message> findByFromLogin(String userName);

    List<Message> findByToLogin (String userName);

}
