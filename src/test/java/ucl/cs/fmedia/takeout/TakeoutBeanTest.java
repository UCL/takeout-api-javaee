package ucl.cs.fmedia.takeout;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import java.io.StringReader;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class TakeoutBeanTest {

  @Tested
  private TakeoutBean takeoutBean;

  @Injectable
  private EntityManager entityManager;

  @Test
  public void testPersistEntryEntityManagerCalled() {
    String jsonString = "{\"startDate\": \"2019-01-01T11:34:54.723Z\", \"totalQueries\": 100, \"totalsByDate\": {\"2019-01-01\": 123}}";
    JsonObject jsonObject = Json.createReader(new StringReader(jsonString))
      .readObject();
    new Expectations() {{
      entityManager.persist((TakeoutEntity) withNotNull());
      result = null;
    }};
    takeoutBean.persistEntry(jsonObject);
  }

  @Test
  public void tsetPersistEntryMissingElements() {
    String jsonString = "{\"startDate\": \"2019-01-01T11:34:54.723Z\", \"totalQueries\": 100}";
    JsonObject jsonObject = Json.createReader(new StringReader(jsonString))
      .readObject();
    assertThrows(NoSuchElementException.class, () -> takeoutBean.persistEntry(jsonObject));
  }

}
