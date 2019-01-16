package ucl.cs.fmedia.takeout;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

public class CorsFilter implements ContainerResponseFilter {


  @Override
  public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {

    if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
      response.getHeaders().add("Access-Control-Allow-Credentials", "true");
      response.getHeaders().add("Access-Control-Allow-Methods", "PUT");
      response.getHeaders().add("Access-Control-Allow-Origin", "*");
      return;
    }

    response.getHeaders().add("Access-Control-Allow-Origin", "*");
  }
}
