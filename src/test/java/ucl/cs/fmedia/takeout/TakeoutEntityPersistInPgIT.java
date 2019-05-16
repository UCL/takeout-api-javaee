package ucl.cs.fmedia.takeout;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class TakeoutEntityPersistInPgIT {

  private final EntityManager entityManager = Persistence.createEntityManagerFactory("takeout-api_PU_IT")
    .createEntityManager();

  /**
   * Scenario: A complete TakeoutEntity can be persisted in PostgreSQL
   */
  @Test
  public void testPersistEntityInPostgresSQL() {
    TakeoutEntity entity = new TakeoutEntity();
    LocalDate now = LocalDate.now();
    entity.setStartDate(now);
    entity.setTotalQueries(100);
    JsonObject jsonObject = Json.createObjectBuilder().add("test-id", "test-value").build();
    entity.setTotalsByDate(jsonObject);
    entityManager.getTransaction().begin();
    entityManager.persist(entity);
    entityManager.getTransaction().commit();
  }

  @AfterEach
  public void removeEntityFromDatabase() {
    entityManager.getTransaction().begin();
    TakeoutEntity takeoutentity = entityManager.createQuery(
      "SELECT t FROM TakeoutEntity t ORDER BY t.id DESC", TakeoutEntity.class
    ).setMaxResults(1).getResultList().get(0);
    entityManager.remove(takeoutentity);
    entityManager.getTransaction().commit();
  }


}
