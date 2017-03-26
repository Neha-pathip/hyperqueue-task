/*
 * HyperQueue API
 * Network enabled in-memory event queuing system.
 */

package hyperqueue.model;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ApiException extends WebApplicationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1997902552941637339L;

	private int code;

	public ApiException(int code, String msg) {
		super(Response.status(Response.Status.BAD_REQUEST)
	             .entity(msg).type(MediaType.TEXT_PLAIN).build());
		
		this.setCode(code);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
