package ucl.cs.fmedia.takeout;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Path("/takeout")
@RequestScoped
public class TakeoutResource {

  @EJB
  private TakeoutBean takeoutBean;

  @PUT
  @Path("submit")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response putTakeoutData(final JsonObject entry) {
    takeoutBean.persistEntry(entry);
    return Response.ok().build();
  }

}
