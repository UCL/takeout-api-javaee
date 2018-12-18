package ucl.cs.fmedia.takeout;

import java.lang.reflect.Field;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
            () -> assertNotNull(clTakeout.getDeclaredMethod("getId"))
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
            GenerationType.AUTO,
            generatedValue.strategy(),
            "Expect @GeneratedValue configured as strategy = GenerationType.AUTO"
    );
    assertEquals("", generatedValue.generator());
    assertNotNull(field.getAnnotation(Id.class), "Expect field to be annotated as @Id");
  }

}
