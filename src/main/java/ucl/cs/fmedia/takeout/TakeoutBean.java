package ucl.cs.fmedia.takeout;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

/**
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Stateless
public class TakeoutBean {

  private final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT.withZone(ZoneId.systemDefault());

  @PersistenceContext
  private EntityManager entityManager;

  public void persistEntry(JsonObject entry) {
    TakeoutEntity entity = new TakeoutEntity();
    ZonedDateTime startDateZoned = ZonedDateTime.parse(entry.getString("startDate"), formatter);
    LocalDate startDate = startDateZoned.toLocalDate();
    entity.setStartDate(startDate);
    entity.setTotalQueries(entry.getInt("totalQueries"));
    if (!entry.containsKey("totalsByDate")) {
      throw new NoSuchElementException("totalsByDate JsonArray is missing");
    }
    JsonArray jsonArray = entry.getJsonArray("totalsByDate");
    // wrap array in an object
    JsonObject jsonTotalsByDate = Json.createObjectBuilder().add("totalsByDate", jsonArray).build();
    entity.setTotalsByDate(jsonTotalsByDate);
    entityManager.persist(entity);
  }

}
