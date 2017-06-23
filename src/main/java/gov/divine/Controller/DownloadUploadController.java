package gov.divine.Controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/main")
@Scope("session")
public class DownloadUploadController {

    private String filePath = System.getProperty("user.dir")+File.separator+
                                "src"+File.separator+
                                "main"+File.separator+
                                "resources"+File.separator+
                                "downloads";

    @GetMapping("/downloadFile/{fileName:.+}")
    public void downloadFile(HttpServletResponse response,
                                   @PathVariable("fileName") String fileName){
        Path file = Paths.get(filePath+File.separator+fileName);
        if (Files.exists(file))
        {
            response.setContentType("multipart/form-data");
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
            try
            {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @PostMapping(value = "/uploadFile")
    @ResponseBody
    public Map<String, String> uploadFile(@RequestParam("file")MultipartFile file){
        if(file != null){
            String fileName = new Date().getTime()+"_"+file.getOriginalFilename();
            try (InputStream inputStream = new BufferedInputStream(file.getInputStream());
                 OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath+File.separator+fileName)))
            {
                byte[] buffer = new byte[256];
                while (inputStream.read(buffer) != -1) {
                    outputStream.write(buffer);
                }
                return Collections.singletonMap("file", fileName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return Collections.singletonMap("file", null);
    }
}
