package hyperqueue.resources;

import hyperqueue.model.ApiException;
import hyperqueue.services.BrokerService;
import hyperqueue.services.factories.BrokerServiceFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

@Path("/{topicName:([^/]+?)?}")

@Produces({ "application/json" })
public class HyperQueueApi {
	private final BrokerService delegate = BrokerServiceFactory.getBroker();

	@GET

	@Produces({ "application/json" })
	public Response topicNameGet(
			@PathParam("topicName") String topicName,
			@HeaderParam("SessionID") String sessionID) throws ApiException {
		return delegate.topicNameGet(topicName, sessionID);
	}

	@POST

	@Produces({ "application/json" })
	@Consumes({ "application/json", "text/plain; charset=UTF-8" })
	public Response topicNamePost(
			@PathParam("topicName") String topicName,
			String message) throws ApiException {
		return delegate.topicNamePost(topicName, message);
	}
}
