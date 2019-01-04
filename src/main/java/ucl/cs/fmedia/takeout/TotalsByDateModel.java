package ucl.cs.fmedia.takeout;

import javax.json.bind.annotation.JsonbDateFormat;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class TotalsByDateModel implements Serializable {

  private int totalPerDay;

  @JsonbDateFormat("yyyy-MM-dd")
  private Date date;

  public int getTotalPerDay() {
    return totalPerDay;
  }

}
