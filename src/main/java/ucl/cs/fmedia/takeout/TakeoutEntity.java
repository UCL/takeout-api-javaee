package ucl.cs.fmedia.takeout;

import java.io.Serializable;
import java.time.LocalDate;
import javax.json.JsonArray;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Entity
public class TakeoutEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Convert(converter = JsonObjectConverter.class)
  private JsonArray totalsByDate;

  private Integer totalQueries;

  private LocalDate startDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setTotalsByDate(JsonArray totalsByDate) { this.totalsByDate = totalsByDate; }

  public void setTotalQueries(Integer totalQueries) { this.totalQueries = totalQueries; }

  public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof TakeoutEntity)) {
      return false;
    }
    TakeoutEntity other = (TakeoutEntity) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ucl.cs.fmedia.takeout.TakeoutEntity[ id=" + id + " ]";
  }

}
