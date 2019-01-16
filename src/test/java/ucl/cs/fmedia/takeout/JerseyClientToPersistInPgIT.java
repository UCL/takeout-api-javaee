package ucl.cs.fmedia.takeout;

import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.Response;
import java.io.StringReader;
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
    String jsonString = "{\"startDate\": \"2019-02-01\", \"totalQueries\": 200, \"totalsByDate\": [{\"date\": \"2019-02-01\"}]}";
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
    JsonArray totalsByDate = takeoutentity.get(0).getTotalsByDate().getJsonArray("totalsByDate");
    assertEquals(1, totalsByDate.size());
    assertEquals("2019-02-01", totalsByDate.getJsonObject(0).getString("date"));
  }

  @Test
  public void testCorsRequest() {
    String jsonString = "{\"startDate\": \"2019-02-01\", \"totalQueries\": 200, \"totalsByDate\": [{\"date\": \"2019-02-01\"}]}";
    JsonObject jsonObject = Json.createReader(new StringReader(jsonString))
      .readObject();
    System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
    Response response = client.target("http://localhost:8081/takeout-api-javaee")
      .path("/takeout/submit")
      .request()
      .header("Origin", "http://test.uk")
      .header("Access-Control-Request-Method", "PUT")
      .put(Entity.json(jsonObject));
    assertEquals(200, response.getStatus());
    assertEquals("*", response.getHeaderString("Access-Control-Allow-Origin"));
    entityManager.getTransaction().begin();
    List<TakeoutEntity> takeoutentity = entityManager.createQuery(
      "SELECT t FROM TakeoutEntity t ORDER BY t.id DESC", TakeoutEntity.class
    ).setMaxResults(1).getResultList();
    assertEquals(1, takeoutentity.size());
    assertEquals("2019-02-01", takeoutentity.get(0).getStartDate().toString());
    assertEquals(200, takeoutentity.get(0).getTotalQueries().intValue());
    JsonArray totalsByDate = takeoutentity.get(0).getTotalsByDate().getJsonArray("totalsByDate");
    assertEquals(1, totalsByDate.size());
    assertEquals("2019-02-01", totalsByDate.getJsonObject(0).getString("date"));
  }

}
