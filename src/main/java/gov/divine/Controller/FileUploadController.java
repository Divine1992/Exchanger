package gov.divine.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
public class FileUploadController {
    private final String filePath = "D://";

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file){
        try ( InputStream inputStream = new BufferedInputStream(file.getInputStream());
              OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath+file.getOriginalFilename())))
            {
                byte[] buffer = new byte[256];
                while (inputStream.read(buffer) != -1) {
                    outputStream.write(buffer);
                }
            } catch (IOException ex) {
                    ex.printStackTrace();
            }
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}
