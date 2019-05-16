package ucl.cs.fmedia.takeout;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class LocalDateConverterTest {

  @Test
  public void testConvertToDatabaseColumn() {
    long epochDays = 18030L;
    LocalDate localDate = LocalDate.ofEpochDay(epochDays);
    long epochMilis = epochDays * 24 * 60 * 60 * 1000;
    Date expected = new Date(epochMilis);
    LocalDateConverter localDateConverter = new LocalDateConverter();
    Date actual = localDateConverter.convertToDatabaseColumn(localDate);
    assertEquals(expected.toString(), actual.toString());
  }

  @Test
  public void testConvertToEntityAttribute() {
    long epochDays = 18030L;
    LocalDate expected = LocalDate.ofEpochDay(epochDays);
    long epochMilis = epochDays * 24 * 60 * 60 * 1000;
    Date date = new Date(epochMilis);
    LocalDateConverter localDateConverter = new LocalDateConverter();
    LocalDate actual = localDateConverter.convertToEntityAttribute(date);
    assertEquals(expected.toString(), actual.toString());
  }

}
