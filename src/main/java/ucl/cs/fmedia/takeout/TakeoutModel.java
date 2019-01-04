package ucl.cs.fmedia.takeout;

import javax.json.bind.annotation.JsonbDateFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class TakeoutModel implements Serializable {

  private int totalQueries;

  @JsonbDateFormat("yyyy-MM-dd")
  private Date startDate;

  private List<TotalsByDateModel> totalsByDate;

  public int getTotalQueries() {
    return totalQueries;
  }

  public void setTotalQueries(int totalQueries) {
    this.totalQueries = totalQueries;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public List<TotalsByDateModel> getTotalsByDate() {
    return totalsByDate;
  }

  public void setTotalsByDate(List<TotalsByDateModel> totalsByDate) {
    this.totalsByDate = totalsByDate;
  }

}
