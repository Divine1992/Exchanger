package gov.divine.Service;

import gov.divine.Model.Message;

import java.util.List;

public interface MessageService {
    List<List<Message>> findByFromLoginOrToLogin (); //all

    List<Message> findByFromLogin ();  //send

    List<Message> findByToLogin (); //recieve

    boolean save(Message message);
}
