package gov.divine.Service;

import gov.divine.Model.Message;

import java.util.List;

public interface MessageService {
    List<Message> findByFromLoginOrToLogin (); //all

    List<Message> findByFromLogin ();  //send

    List<Message> findByToLogin (); //recieve

    void save(Message message);
}
