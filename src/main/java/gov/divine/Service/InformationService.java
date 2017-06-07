package gov.divine.Service;

import gov.divine.Model.Information;

import java.util.List;

public interface InformationService {
    boolean save(Information information);
    List<List<Information>> getSubscribersInfo();
    List<List<Information>> getMyInfo(Long senderUserId);
}
