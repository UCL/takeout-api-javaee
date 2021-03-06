package ucl.cs.fmedia.takeout;

import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JerseyClientToPersistInPgIT {

  private final Logger logger = Logger.getLogger(getClass().getName());
  private final Feature feature = new LoggingFeature(logger, Level.INFO, null, null);
  private final Client client = ClientBuilder.newBuilder()
    .register(feature)
    .build();
  private final EntityManager entityManager = Persistence.createEntityManagerFactory("takeout-api_PU_IT")
    .createEntityManager();

  @Test
  public void testFromHttpPostToPersistInPg() {
    String jsonString = "{\"startDate\": \"2019-02-01T11:34:54.723Z\", \"totalQueries\": 200, \"totalsByDate\": {\"2019-02-01\": 123}}";
    JsonObject jsonObject = Json.createReader(new StringReader(jsonString))
      .readObject();
    Response response = client.target("http://localhost:8081/takeout-api-javaee")
      .path("/takeout/submit")
      .request()
      .put(Entity.json(jsonObject));
    assertEquals(200, response.getStatus());
    entityManager.getTransaction().begin();
    List<TakeoutEntity> takeoutentity = entityManager.createQuery(
      "SELECT t FROM TakeoutEntity t ORDER BY t.id DESC", TakeoutEntity.class
    ).setMaxResults(1).getResultList();
    assertEquals(1, takeoutentity.size());
    assertEquals("2019-02-01", takeoutentity.get(0).getStartDate().toString());
    assertEquals(200, takeoutentity.get(0).getTotalQueries().intValue());
    JsonObject totalsByDate = takeoutentity.get(0).getTotalsByDate();
    assertEquals(123, totalsByDate.getInt("2019-02-01"));
    entityManager.remove(takeoutentity.get(0));
    entityManager.getTransaction().commit();
  }

  /**
   * The property startDate cannot accept future dates
   */
  @Test
  public void testFromHttpPostStartDateInFutureToPersistInPg() {
    LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
    String jsonTemplate = "{\"startDate\": \"%sZ\", \"totalQueries\": 100, \"totalsByDate\": {\"2019-01-01\": 123}}";
    String jsonString = String.format(jsonTemplate, tomorrow.toString());
    JsonObject jsonObject = Json.createReader(new StringReader(jsonString))
      .readObject();
    Response response = client.target("http://localhost:8081/takeout-api-javaee")
      .path("/takeout/submit")
      .request()
      .put(Entity.json(jsonObject));
    assertEquals(400, response.getStatus());
    entityManager.getTransaction().begin();
    List<TakeoutEntity> takeoutentity = entityManager.createQuery(
      "SELECT t FROM TakeoutEntity t ORDER BY t.id DESC", TakeoutEntity.class
    ).getResultList();
    assertEquals(0, takeoutentity.size());
  }
}
