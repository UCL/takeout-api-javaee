package ucl.cs.fmedia.takeout;

import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

/**
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Stateless
public class TakeoutBean {

  private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

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

  public void persistEntry(JsonObject entry) {
    TakeoutEntity entity = new TakeoutEntity();
    LocalDate startDate = LocalDate.parse(entry.getString("startDate"), formatter);
    entity.setStartDate(startDate);
    entity.setTotalQueries(entry.getInt("totalQueries"));
    if (!entry.containsKey("totalsByDate")) {
      throw new NoSuchElementException("totalsByDate JsonArray is missing");
    }
    entity.setTotalsByDate(entry.getJsonArray("totalsByDate"));
    entityManager.persist(entity);
  }

}
