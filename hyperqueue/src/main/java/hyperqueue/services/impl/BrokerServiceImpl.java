/*
 * HyperQueue API
 * Network enabled in-memory event queuing system.
 */

package hyperqueue.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.core.Response;
import org.slf4j.LoggerFactory;

import hyperqueue.model.ApiException;
import hyperqueue.services.BrokerService;

public class BrokerServiceImpl extends BrokerService {

	ConcurrentHashMap<String, List<String>> topicToEvent = new ConcurrentHashMap<String, List<String>>();
	ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> idToOffset = new ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>();

	@Override
	public Response topicNameGet(String topicName, String sessionID) throws ApiException {
		handleEmptyTopicName(topicName);
		String event = null;
		if (Objects.isNull(sessionID)) {
			sessionID = UUID.randomUUID().toString();
			idToOffset.put(sessionID, new ConcurrentHashMap<String, Integer>());
			LoggerFactory.getLogger(getClass()).info("New Session ID : " + sessionID);
		}
		// else { on every first request we are going to send the data or is not
		// specified so sending the first event as well
		try {
			event = getEvent(sessionID, topicName);
			if (Objects.nonNull(event)) {
				LoggerFactory.getLogger(getClass()).info("Returning event. SessionID = " + sessionID + " Offset = "
						+ idToOffset.get(sessionID).get(topicName) + " Topic = " + topicName + " Event = " + event);
			} else {
				LoggerFactory.getLogger(getClass()).info("could not find any events");
			}
		} catch (InterruptedException e) {
			LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
		}
		// }
		// return Response.ok().header("SessionID", sessionID).entity(new
		// ApiResponseMessage(ApiResponseMessage.OK, event)).build(); better way
		// to handle the responses
		return Response.ok().header("SessionID", sessionID.trim()).header("X-SessionID", sessionID.trim()).entity(event)
				.build();
	}

	@Override
	public Response topicNamePost(String topicName, String message) throws ApiException {
		handleEmptyTopicName(topicName);
		if (message != null) {
			this.addEvent(message, topicName);
			LoggerFactory.getLogger(getClass()).info("Event added. Topic = " + topicName + " Event = " + message);
		} else {
			LoggerFactory.getLogger(getClass()).info("Error while adding event");
		}
		return Response.ok().entity("Received Event " + message).build();
	}

	public void handleEmptyTopicName(String topicName) throws ApiException {
		if (Objects.isNull(topicName) || topicName.length() <= 0) {
			throw new ApiException(1, "TopicName not provided, please provide a topic");
		}
	}

	private void addEvent(String event, String topicName) {
		// add event to existing topic
		if (topicToEvent.containsKey(topicName)) {
			List<String> topicQueue = (List<String>) Collections.synchronizedList(topicToEvent.get(topicName));
			topicQueue.add(event);
		} else {
			// create a new list
			List<String> topicQueue = (List<String>) Collections.synchronizedList(new ArrayList<String>());
			topicQueue.add(event);
			topicToEvent.put(topicName, topicQueue);
		}
	}

	private String getEvent(String id, String topic) throws InterruptedException {
		int offset = -1;
		ConcurrentHashMap<String, Integer> map = null;
		if (idToOffset.containsKey(id)) {
			map = idToOffset.get(id);
		} else {
			return null;
		}
		
		String event = null;
		if (Objects.nonNull(topicToEvent.get(topic))) {
			List<String> q = (List<String>) Collections.synchronizedList(topicToEvent.get(topic));
			if(Objects.isNull(q))
			{
				return null;
			}
			if (Objects.nonNull(map) && map.containsKey(topic)) {
				offset = map.get(topic);
			} 
			if ((offset + 1) >= q.size()) {
				Thread.sleep(1000);
				if ((offset + 1) < q.size()) {
					// Producer sends a new event to consumer
					event = (String) q.get(offset + 1);
				} else {
					return null;
				}
			} else {
				event = (String) q.get(offset + 1);
			}
			map.put(topic, offset + 1);
			idToOffset.put(id, map);
		}
		return event;
	}

}
