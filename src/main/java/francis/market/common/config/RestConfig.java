package francis.market.common.config;

import francis.market.common.exception.exceptionmapper.MarketExceptionMapper;
import francis.market.project.resource.ProjectResource;
import francis.market.user.resource.UserResource;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Jersey resource configuration class.
 *
 */
@ApplicationPath("/market")
public class RestConfig extends ResourceConfig{

    public RestConfig() {
        registerClasses(UserResource.class, ProjectResource.class, MarketExceptionMapper.class);
    }
}
