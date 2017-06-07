package gov.divine.Controller;

import gov.divine.Model.Message;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/exchanger/main")
@Scope("session")
public class DownloadController {

    @PostMapping("/downloadMessage")
    public HttpStatus download(@RequestBody @Valid Message message, HttpServletResponse response){
        Path file = Paths.get(message.getFile());
        if (Files.exists(file))
        {
            System.out.println(file);
            response.setContentType("multipart/form-data");
            response.addHeader("Content-Disposition", "attachment; filename="+message.getFile());
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
}
