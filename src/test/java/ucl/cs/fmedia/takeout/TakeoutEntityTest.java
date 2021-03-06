package ucl.cs.fmedia.takeout;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for @see ucl.cs.fmedia.takeout.TakeoutEntity based on the recommendations listed in
 * <a href="http://mjremijan.blogspot.com/2016/06/unit-testing-jpa-annotationsstop_20.html">
 * http://mjremijan.blogspot.com/2016/06/unit-testing-jpa-annotationsstop_20.html
 * </a>
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class TakeoutEntityTest {

  /**
   * Test TakeoutEntity is annotated as @Entity
   */
  @Test
  public void hasEntityAnnotation() {
    Class<TakeoutEntity> clTakeout = TakeoutEntity.class;
    Entity annotation = clTakeout.getAnnotation(Entity.class);
    assertNotNull(annotation, "Expect TakeoutEntity to be annotated as @Entity");
    assertEquals("", annotation.name(), "Expect name to be empty");
  }

  /**
   * Test TakeoutEntity has all required fields
   */
  @Test
  public void hasRequiredFields() {
    Class<TakeoutEntity> clTakeout = TakeoutEntity.class;
    assertAll("Expect TakeoutEntity to have all required fields",
            () -> assertNotNull(clTakeout.getDeclaredField("id")),
            () -> assertNotNull(clTakeout.getDeclaredField("totalsByDate")),
            () -> assertNotNull(clTakeout.getDeclaredField("totalQueries")),
            () -> assertNotNull(clTakeout.getDeclaredField("startDate"))
    );
  }

  /**
   * Test TakeoutEntity has all required methods
   */
  @Test
  public void hasRequiredMethods() {
    Class<TakeoutEntity> clTakeout = TakeoutEntity.class;
    assertAll("Expect TakeoutEntity to have all required methods",
      () -> assertNotNull(clTakeout.getDeclaredMethod("getId")),
      () -> assertNotNull(clTakeout.getDeclaredMethod("setId", Long.class)),
      () -> assertNotNull(clTakeout.getDeclaredMethod("setStartDate", LocalDate.class)),
      () -> assertNotNull(clTakeout.getDeclaredMethod("setTotalQueries", Integer.class)),
      () -> assertNotNull(clTakeout.getDeclaredMethod("setTotalsByDate", JsonObject.class))
    );
  }

  /**
   * Test id field has all required annotations
   *
   * @throws NoSuchFieldException
   */
  @Test
  public void testId() throws NoSuchFieldException {
    Class<TakeoutEntity> clTakeout = TakeoutEntity.class;
    Field field = clTakeout.getDeclaredField("id");
    GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
    assertEquals(
            GenerationType.SEQUENCE,
            generatedValue.strategy(),
            "Expect @GeneratedValue configured as strategy = GenerationType.AUTO"
    );
    assertEquals("takeoutentity_id_seq", generatedValue.generator());
    assertNotNull(field.getAnnotation(Id.class), "Expect field to be annotated as @Id");
  }

  /**
   * Test totalsByDate has all required annotations
   *
   * @throws NoSuchFieldException
   */
  @Test
  public void testTotalsByDate() throws NoSuchFieldException {
    Class<TakeoutEntity> clTakeout = TakeoutEntity.class;
    Field field = clTakeout.getDeclaredField("totalsByDate");
    Convert convert = field.getAnnotation(Convert.class);
    assertEquals(
      JsonObjectConverter.class,
      convert.converter(),
      "Expect field to be configured with a @Convert annotation, set to JsonObjectConverter"
    );
  }

  @Test
  public void testStartDateValidator() {
    TakeoutEntity takeoutEntity = new TakeoutEntity();
    takeoutEntity.setId(0L);
    takeoutEntity.setStartDate(LocalDate.now().plusDays(1));
    takeoutEntity.setTotalQueries(1);
    String jsonString = "{\"startDate\": \"2019-01-01T11:34:54.723Z\", \"totalQueries\": 100, \"totalsByDate\": {\"2019-01-01\": 123}}";
    JsonObject jsonObject = Json.createReader(new StringReader(jsonString))
      .readObject();
    takeoutEntity.setTotalsByDate(jsonObject);
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    Set<ConstraintViolation<TakeoutEntity>> constraintViolationSet = validator.validate(takeoutEntity);
    assertEquals( 1, constraintViolationSet.size() );
    assertEquals( "startDate cannot be in the future", constraintViolationSet.iterator().next().getMessage() );
  }

}
