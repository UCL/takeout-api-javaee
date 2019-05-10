package ucl.cs.fmedia.takeout;

import java.io.Serializable;
import java.time.LocalDate;
import javax.json.JsonObject;
import javax.persistence.*;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Entity
public class TakeoutEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "takeoutentity_id_seq")
  @SequenceGenerator(name = "takeoutentity_id_seq", sequenceName = "takeoutentity_id_seq", allocationSize = 1)
  private Long id;

  @Convert(converter = JsonObjectConverter.class)
  private JsonObject totalsByDate;

  private Integer totalQueries;

  @Convert(converter = LocalDateConverter.class)
  private LocalDate startDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public JsonObject getTotalsByDate() { return totalsByDate; }

  public void setTotalsByDate(JsonObject totalsByDate) { this.totalsByDate = totalsByDate; }

  public Integer getTotalQueries() { return totalQueries; }

  public void setTotalQueries(Integer totalQueries) { this.totalQueries = totalQueries; }

  public LocalDate getStartDate() { return startDate; }

  public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += id != null ? id.hashCode() : 0;
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof TakeoutEntity)) {
      return false;
    }
    TakeoutEntity other = (TakeoutEntity) object;
    if (this.id == null && other.id != null) {
      return false;
    }
    if (this.id != null && !this.id.equals(other.id)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ucl.cs.fmedia.takeout.TakeoutEntity[ id=" + id + " ]";
  }

}
