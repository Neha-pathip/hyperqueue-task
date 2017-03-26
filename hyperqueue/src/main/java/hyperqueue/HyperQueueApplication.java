package hyperqueue;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import hyperqueue.resources.ApiOriginFilter;
import hyperqueue.resources.JacksonJsonProvider;

@ApplicationPath("broker")
public class HyperQueueApplication extends ResourceConfig {

	public HyperQueueApplication() {
		super();
		packages("hyperqueue.resources");
		register(ApiOriginFilter.class);
		register(JacksonJsonProvider.class);
	
	}

}
