/*
 * HyperQueue API
 * Network enabled in-memory event queuing system.
 */

package hyperqueue.services;

import javax.ws.rs.core.Response;
import hyperqueue.model.ApiException;

public abstract class BrokerService {
	public abstract Response topicNameGet(String topicName, String session) throws ApiException;

	public abstract Response topicNamePost(String topicName, String securmessageityContext) throws ApiException;
}
