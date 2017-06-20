package gov.divine.Controller;

import gov.divine.Model.Message;
import gov.divine.Service.MessageService;
import gov.divine.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exchanger/main")
@Scope("request")
public class MessageController {

    @Value("${file.path}")
    private String filePath;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @GetMapping("/{senderUserId}/sendMessages")
    public List<List<Message>> sendMessages(@PathVariable String senderUserId){
        return messageService.sendMessages(Long.valueOf(senderUserId));
    }

    @GetMapping("/{senderUserId}/receiveMessages")
    public List<List<Message>> receiveMessages(@PathVariable String senderUserId){
        return messageService.recieveMessages(Long.valueOf(senderUserId));
    }

    @PostMapping(value = "/sendMessage")
    @ResponseBody
    public Map<String, String> sendMessage(@RequestBody @Valid Message message) {
        Map<String, String> results = new HashMap<>();
        if (messageService.save(message)) {
            results.put("isSuccess", "true");
            results.put("body", "Повідомлення успішно відправлено");
            return results;
        }
        results.put("isSuccess", "false");
        results.put("body", "Помилка відправлення повідомлення");
        return results;
    }

}
