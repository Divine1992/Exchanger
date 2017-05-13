package gov.divine.Controller;

import gov.divine.Model.Message;
import gov.divine.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/exchanger/main")
@Scope("request")
public class MessageController {

    private final String filePath = "D://";

    @Autowired
    MessageService messageService;

    @GetMapping("/allMessages")
    public List<Message> allMessages(){
        return messageService.findByFromLoginOrToLogin();
    }

    @GetMapping("/receiveMessages")
    public List<Message> receiveMessages(){
        return messageService.findByToLogin();
    }

    @GetMapping("/sendMessages")
    public List<Message> sendMessages(){
        return messageService.findByFromLogin();
    }

    @PostMapping("/send")
    public void save(@RequestBody Message message){
        messageService.save(message);
    }

    @PostMapping(value = "/upload", produces="text/plain")
    public String uploadFile(@RequestParam("file")MultipartFile file){
        if(file != null){
            try (InputStream inputStream = new BufferedInputStream(file.getInputStream());
                 OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath+file.getOriginalFilename())))
            {
                byte[] buffer = new byte[256];
                while (inputStream.read(buffer) != -1) {
                    outputStream.write(buffer);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return filePath+file.getOriginalFilename();
        }
        return null;
    }

}
