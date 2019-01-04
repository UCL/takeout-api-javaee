package ucl.cs.fmedia.takeout;

import mockit.Mock;
import mockit.MockUp;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class TakeoutResourceTest extends JerseyTest {

  @Override
  protected Application configure() { return new ResourceConfig(TakeoutResource.class); }

  /**
   * Test that the URL /takeout/submit calls TakeoutResource::putTakeoutData
   */
  @Test
  public void testUrlForTakeoutSubmit() {
    new MockUp<TakeoutResource>() {
      @Mock
      public Response putTakeoutData(JsonObject entry) {
        return Response.ok().build();
      }
    };
    String jsonString = "{\"startDate\": \"2019-01-01\", \"totalQueries\": 100, \"queriesPerDate\": []}";
    JsonObject jsonObject = Json.createReader(new StringReader(jsonString))
      .readObject();
    Response response = target("/takeout/submit").request().put(Entity.json(jsonObject));
    assertEquals(response.getStatus(), 200);
  }

}
