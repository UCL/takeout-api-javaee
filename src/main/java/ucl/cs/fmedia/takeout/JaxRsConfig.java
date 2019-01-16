package ucl.cs.fmedia.takeout;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("")
public class JaxRsConfig extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    final Set<Class<?>> classes = new HashSet<>();
    classes.add(TakeoutResource.class);
    return classes;
  }

  @Override
  public Set<Object> getSingletons() {
    final Set<Object> singletons = new HashSet<>();
    singletons.add(new CorsFilter());
    return singletons;
  }

}
