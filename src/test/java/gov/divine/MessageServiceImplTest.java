package gov.divine;

import gov.divine.Model.Message;
import gov.divine.Service.MessageServiceImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MessageServiceImplTest {

    @Test
    public void testCorrectSeparateList(){
        List<Message> allList = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            allList.add(new Message());
        }
        MessageServiceImpl messageService = new MessageServiceImpl();
        List<List<Message>> results = messageService.separateList(allList);
        assertEquals(11, results.get(results.size()-1).size());
    }
}
