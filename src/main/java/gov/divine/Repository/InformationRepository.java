package gov.divine.Repository;

import gov.divine.Model.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("informationRepository")
public interface InformationRepository extends JpaRepository<Information, Long>{
}
