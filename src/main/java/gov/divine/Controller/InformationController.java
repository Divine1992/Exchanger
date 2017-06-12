package gov.divine.Controller;

import gov.divine.Model.Information;
import gov.divine.Service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/exchanger/main")
@Scope("request")
public class InformationController {

    @Autowired
    private InformationService informationService;

    @PostMapping(value = "/postInformation", produces = "text/plain")
    public String save(@RequestBody @Valid Information information){
        if (informationService.save(information)){
            return "Дані успішно додані";
        }
        return "Помилка додавання інформації";
    }

    @GetMapping("/{senderUserId}/getMyInformations")
    @ResponseBody
    public List<List<Information>> getMyInfo(@PathVariable String senderUserId){
        return informationService.getMyInfo(Long.valueOf(senderUserId));
    }

    @GetMapping("/getSubscribersInfo")
    @ResponseBody
    public List<List<Information>> getSubscribersInfo(){
        return informationService.getSubscribersInfo();
    }
}
