package webservices;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
import org.glassfish.jersey.jackson.JacksonFeature;

@ApplicationPath("api")
public class ApplicationMain extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        // Enregistrement des providers JSON
        resources.add(JacksonFeature.class);
        resources.add(org.glassfish.jersey.jackson.JacksonFeature.class);
        resources.add(com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider.class);
        
        // Enregistrement de vos ressources
        resources.add(HelloRessources.class);
        resources.add(UERessources.class);
        resources.add(ModuleRessources.class);
        
        return resources;
    }
}
