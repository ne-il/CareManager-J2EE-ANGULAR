package application;

import application.authentication.rest.StaffAuthenticationRest;
import application.document.rest.DocumentRestService;
import application.filters.CORSFilter;
import application.node.rest.NodeRestService;
import application.patient.rest.PatientRestService;
import application.staff.repository.StaffRepository;
import application.staff.rest.StaffRestService;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rs")
public class ApplicationConfig extends Application {
    private final Set<Class<?>> classes;

    public ApplicationConfig() {
        HashSet<Class<?>> c = new HashSet<>();
        c.add(JacksonFeature.class);
        c.add(DocumentRestService.class);
        c.add(StaffAuthenticationRest.class);
        c.add(PatientRestService.class);
        c.add(NodeRestService.class);
        c.add(StaffRestService.class);
        c.add(CORSFilter.class);
        classes = Collections.unmodifiableSet(c);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}