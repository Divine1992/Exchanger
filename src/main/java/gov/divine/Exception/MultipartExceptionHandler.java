package gov.divine.Exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MultipartExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${spring.http.multipart.max-file-size}")
    private String maxFileSize;

    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public String handleFileException(HttpServletRequest request, MultipartException ex) {
        System.err.println("Error");
        return "Файл занадто великий, максимальний розмір файлу "+maxFileSize;
    }

}