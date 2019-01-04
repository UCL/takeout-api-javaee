package ucl.cs.fmedia.takeout;

import org.postgresql.util.PGobject;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Converter
public class JsonObjectConverter implements AttributeConverter<JsonObject, Object> {

  @Override
  public Object convertToDatabaseColumn(JsonObject jsonObject) {
    PGobject pgObject = new PGobject();
    pgObject.setType("json");
    Optional<JsonObject> jsonOptional = Optional.ofNullable(jsonObject);
    String value = jsonOptional.map(JsonObject::toString).get();
    try {
      pgObject.setValue(value);
    } catch (SQLException e) {
      throw new IllegalArgumentException("Unable to serialise jsonObject input", e);
    }
    return pgObject;
  }

  @Override
  public JsonObject convertToEntityAttribute(Object o) {
    if (o instanceof PGobject && "json".equals(((PGobject) o).getType())) {
      String value = ((PGobject) o).getValue();
      JsonReader jsonReader = Json.createReader(new StringReader(value));
      return jsonReader.readObject();
    } else {
      throw new IllegalArgumentException("Unable to deserialise jsonObject input");
    }
  }
}
