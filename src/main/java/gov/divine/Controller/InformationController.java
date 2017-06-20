package gov.divine.Controller;

import gov.divine.Model.Information;
import gov.divine.Service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exchanger/main")
@Scope("request")
public class InformationController {

    @Autowired
    private InformationService informationService;

    @PostMapping(value = "/postInformation")
    @ResponseBody
    public Map<String, String> save(@RequestBody @Valid Information information){
        Map<String, String> results = new HashMap<>();
        if (informationService.save(information)){
            results.put("isSuccess","true");
            results.put("body","Дані успішно опубліковані");
            return results;
        }
        results.put("isSuccess","false");
        results.put("body","Помилка опублікування даних");
        return results;
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
