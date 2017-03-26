/*
 * HyperQueue API
 * Network enabled in-memory event queuing system.
 */

package hyperqueue.services.factories;

import hyperqueue.services.BrokerService;
import hyperqueue.services.impl.BrokerServiceImpl;

public class BrokerServiceFactory {
	
	private final static BrokerService service = new BrokerServiceImpl();

    public static BrokerService getBroker() {
        return service;
    }

}
