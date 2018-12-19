package ucl.cs.fmedia.takeout;

import org.junit.jupiter.api.Test;
import org.postgresql.util.PGobject;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Converter;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

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
    assertEquals("{\"id\":1}", ((PGobject) result).getValue());
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

  @Test
  public void testConvertToEntityAttributeNull() {
    JsonObjectConverter instance = new JsonObjectConverter();
    assertThrows(
      IllegalArgumentException.class,
      () -> instance.convertToEntityAttribute(null)
    );
  }

}
