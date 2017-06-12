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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service("informationService")
@Scope("request")
public class InformationServiceImpl implements InformationService{

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    @Qualifier("informationRepository")
    private InformationRepository informationRepository;
    private User currentUser;

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
        currentUser.getSubscribers().stream().forEach(i ->{
            informationRepository.findByUserId(i.getId()).forEach(k -> allSubscribersInfo.add(k));
        });
        return separateList(allSubscribersInfo);
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
        if (currentUser == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            currentUser = userRepository.findByLogin(auth.getName());
        }
        return currentUser;
    }
}
