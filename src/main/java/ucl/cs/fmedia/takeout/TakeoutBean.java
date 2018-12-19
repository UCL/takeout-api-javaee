package ucl.cs.fmedia.takeout;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Stateless
public class TakeoutBean {

  @PersistenceContext
  private EntityManager entityManager;

  public void addEntry(TakeoutModel model) {
    TakeoutEntity entity = new TakeoutEntity();
    entity.setTotalQueries(model.getTotalQueries());
    LocalDate localDate = model
      .getStartDate()
      .toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDate();
    entity.setStartDate(localDate);
    entityManager.persist(entity);
  }

}
