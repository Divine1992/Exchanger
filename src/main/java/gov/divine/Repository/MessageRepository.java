package gov.divine.Repository;

import gov.divine.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("messageRepository")
public interface MessageRepository extends JpaRepository<Message, Long> {

    /*List<Message> findByFromLoginOrderBySendDateDesc(String userName);

    List<Message> findByToLoginOrderBySendDateDesc(String userName);*/
}
