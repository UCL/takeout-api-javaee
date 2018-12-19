package ucl.cs.fmedia.takeout;

import org.junit.jupiter.api.Test;
import org.postgresql.util.PGobject;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Converter;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for @see ucl.cs.fmedia.takeout.JsonObjectConverter
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class JsonObjectConverterTest {

  /**
   * Test JsonObjectConverter is annotated as @Converter
   */
  @Test
  public void hasConverterAnnotation() {
    Class<JsonObjectConverter> cl = JsonObjectConverter.class;
    Converter annotation = cl.getAnnotation(Converter.class);
    assertNotNull(annotation, "expect JsonObjectConverter to be annotated as @Converter");
  }

  /**
   * Test an instance of JsonObject is converter to PGobject of type json
   */
  @Test
  public void testConvertToDatabaseColumn() {
    JsonObjectConverter instance = new JsonObjectConverter();
    JsonObject jsonObject = Json.createObjectBuilder().add("id", 1).build();
    Object result = instance.convertToDatabaseColumn(jsonObject);
    assertTrue(result instanceof PGobject);
    assertEquals("json", ((PGobject) result).getType(), "PGobject to be of type json");
  }

  /**
   * Test an instance of PGobject of type json is converted to an instance of JsonObject
   * @throws SQLException
   */
  @Test
  public void testConvertToEntityAttribute() throws SQLException {
    JsonObjectConverter instance = new JsonObjectConverter();
    PGobject pgObject = new PGobject();
    pgObject.setType("json");
    pgObject.setValue("{\"id\": 1}");
    JsonObject result = instance.convertToEntityAttribute(pgObject);
    assertEquals(1, result.getInt("id"));
  }

}
