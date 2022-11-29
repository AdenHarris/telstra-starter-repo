package au.com.telstra.simcardactivator.SimCard;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimCardRepository extends CrudRepository<SimCard, Long> {

  List<SimCard> findByCustomerEmail(String customerEmail);
  List<SimCard> findByIccid(String iccid);
  List<SimCard> findByActive(boolean active);
  SimCard findById(long id);
}