package gov.divine.Service;

import gov.divine.Model.Information;
import gov.divine.Repository.InformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("informationService")
@Scope("session")
public class InformationServiceImpl implements InformationService{

    @Autowired
    @Qualifier("informationRepository")
    private InformationRepository informationRepository;

    @Override
    public boolean save(Information information) {
        return informationRepository.save(information) != null;
    }

    @Override
    public List<List<Information>> getSubscribersInfo() {
        return null;
        /*List<Information> informations = informationRepository.findAll().stream().filter();
        List<List<Information>> resultList = new ArrayList<>();
        resultList.add(informations.stream().limit(18).collect(Collectors.toList()));
        System.out.println(resultList);
        return resultList;*/
    }

    @Override
    public List<List<Information>> getMyInfo(Long senderUserId) {
        List<Information> myInformations = informationRepository.findAll().stream()
                .filter(i -> i.getUserId() == senderUserId)
                .sorted(Comparator.comparing(Information::getSendDate).reversed())
                .collect(Collectors.toList());
        return separateList(myInformations);
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
}
