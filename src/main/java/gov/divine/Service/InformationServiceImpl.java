package gov.divine.Service;

import gov.divine.Model.Information;
import gov.divine.Model.User;
import gov.divine.Repository.InformationRepository;
import gov.divine.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service("informationService")
@Scope("request")
public class InformationServiceImpl implements InformationService{
    private String filePath = System.getProperty("user.dir")+ File.separator+
                                "src"+File.separator+
                                "main"+File.separator+
                                "resources"+File.separator+
                                "downloads";
    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    @Qualifier("informationRepository")
    private InformationRepository informationRepository;

    @Override
    public boolean save(Information information) {
        return informationRepository.save(information) != null;
    }

    @Override
    public List<List<Information>> getMyInfo(Long senderUserId) {
        List<Information> myInformations = informationRepository.findAll().stream()
                .filter(i -> i.getUserId() == senderUserId)
                .sorted(Comparator.comparing(Information::getSendDate).reversed())
                .collect(Collectors.toList());
        return separateList(myInformations);
    }

    @Override
    public List<List<Information>> getSubscribersInfo() {
        User currentUser = getCurrentUser();
        List<Information> allSubscribersInfo = new LinkedList<>();
        currentUser.getSubscribers().stream().forEach(i -> informationRepository.findByUserId(i.getId()).forEach(k -> allSubscribersInfo.add(k)));
        allSubscribersInfo.sort(Comparator.comparing(Information::getSendDate).reversed());
        return separateList(allSubscribersInfo);
    }

    @Override
    public List<List<Information>> delete(Long id) {
        Information information = informationRepository.findOne(id);
        if (information.getFile() != null || !Objects.equals(information.getFile(), "")){
            File file = new File(filePath+File.separator+information.getFile());
            file.delete();
        }
        informationRepository.delete(id);
        return getMyInfo(getCurrentUser().getId());
    }

    public static List<List<Information>> separateList(List<Information> allList){
        List<List<Information>> resultList = new ArrayList<>();
        List<Information> addList = new ArrayList<>(11);
        allList.stream().forEach(i -> {
            addList.add(i);
            if (addList.size() > 10 || allList.indexOf(i) == allList.size()-1){
                resultList.add(new ArrayList<>(addList));
                addList.clear();
            }
        });
        return resultList;
    }

     private User getCurrentUser(){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return userRepository.findByLogin(auth.getName());
     }
}
