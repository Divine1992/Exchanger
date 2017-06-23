package gov.divine.Controller;

import gov.divine.Model.Information;
import gov.divine.Model.Message;
import gov.divine.Service.InformationService;
import gov.divine.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/main")
@Scope("request")
public class DeleteController {

    @Autowired
    private InformationService informationService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/deleteInformation/{id}")
    public List<List<Information>> deleteInformation(@PathVariable("id") String id){
        return informationService.delete(Long.parseLong(id));
    }

    @GetMapping("/deleteMessage/{id}")
    public List<List<Message>> deleteMessage(@PathVariable("id") String id){
        return messageService.delete(Long.parseLong(id));
    }
}
