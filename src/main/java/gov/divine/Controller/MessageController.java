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
import java.util.List;

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

    /*@GetMapping("/download/{fileName:.+}")
    public HttpStatus downloadFile(HttpServletResponse response,
                                   @PathVariable("fileName") String fileName){
        Path file = Paths.get(fileName);
        if (Files.exists(file))
        {
            response.setContentType("multipart/form-data");
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
            try
            {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
                return HttpStatus.OK;
            }
            catch (IOException ex) {
                ex.printStackTrace();
                return HttpStatus.FORBIDDEN;
            }
        }
        return HttpStatus.FORBIDDEN;
    }*/

    @PostMapping(value = "/sendMessage", produces = "text/plain")
    public String sendMessage(@RequestBody @Valid Message message){
        if (messageService.save(message)){
            return "Повідомлення успішно відправлено";
        }
        return "Помилка відправлення повідомлення";
    }

    @PostMapping(value = "/upload", produces = "text/plain")
    public String uploadFile(@RequestParam("file")MultipartFile file, @RequestParam("fileType") String fileType){
        if(file != null){
            String directory = createFolder(fileType);
            try (InputStream inputStream = new BufferedInputStream(file.getInputStream());
                 OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(directory+File.separator+file.getOriginalFilename())))
            {
                byte[] buffer = new byte[256];
                while (inputStream.read(buffer) != -1) {
                    outputStream.write(buffer);
                }
                return directory+File.separator+file.getOriginalFilename();
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return null;
    }

    private String createFolder(String fileType){
        String resultFolder = filePath+"Exchanger";
        if (!Files.exists(FileSystems.getDefault().getPath(resultFolder))){
            File folder = new File(resultFolder);
            if(!folder.mkdir()){
                return null;
            }
        }
        resultFolder += File.separator+fileType;
        if(!Files.exists(FileSystems.getDefault().getPath(resultFolder))){
            File folder = new File(resultFolder);
            if(!folder.mkdir()){
                return null;
            }
        }
        LocalDate date = LocalDate.now();
        resultFolder += File.separator+String.valueOf(date.getYear());
        if(!Files.exists(FileSystems.getDefault().getPath(resultFolder))){
            File folder = new File(resultFolder);
            if(!folder.mkdir()){
                return null;
            }
        }
        resultFolder += File.separator+String.valueOf(date.getMonthValue());
        if(!Files.exists(FileSystems.getDefault().getPath(resultFolder))){
            File folder = new File(resultFolder);
            if(!folder.mkdir()){
                return null;
            }
        }
        resultFolder += File.separator+String.valueOf(date.getDayOfMonth());
        if(!Files.exists(FileSystems.getDefault().getPath(resultFolder))){
            File folder = new File(resultFolder);
            if(!folder.mkdir()){
                return null;
            }
        }
        return resultFolder;
    }
}
