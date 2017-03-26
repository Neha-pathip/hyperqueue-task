/*
 * HyperQueue API
 * Network enabled in-memory event queuing system.
 */

package hyperqueue.resources;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

public class ApiOriginFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		MultivaluedMap<String, Object> headers = responseContext.getHeaders();
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, SessionID");
		headers.add("Access-Control-Expose-Headers", "SessionID, X-SessionID");
		headers.add("Access-Control-Allow-Credentials", "true");
		headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
//		headers.add("Access-Control-Max-Age", "1209600");
	}

}
