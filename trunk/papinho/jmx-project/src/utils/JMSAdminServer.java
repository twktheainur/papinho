package utils;

import java.util.Iterator;
import java.util.Vector;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.naming.Context;
import org.exolab.jms.administration.AdminConnectionFactory;
import org.exolab.jms.administration.JmsAdminServerIfc;

/**
 * JMS Administration connection class
 *
 */
public class JMSAdminServer {

	JmsAdminServerIfc admin;
	Context context;

	/**
	 * Constructor
	 * @param url url of the jms server
	 * @param username admin username of the jms server
	 * @param password admin password of the jms server
	 */
	public JMSAdminServer(String url, String username, String password) {
		if (username != null && password != null) {
			try {
				admin = AdminConnectionFactory.create(url, username, password);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		} else {
			try {
				admin = AdminConnectionFactory.create(url);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}

	}

	/**
	 * Lists all the topics on the jms server
	 * @throws JMSException 
	 */
	@SuppressWarnings("rawtypes")
	public void listTopics() throws JMSException {
		Vector destinations = admin.getAllDestinations();
		Iterator iterator = destinations.iterator();
		while (iterator.hasNext()) {
			Destination destination = (Destination) iterator.next();
			if (destination instanceof Topic) {
				Topic topic = (Topic) destination;
				System.out.println("topic:" + topic.getTopicName());
			}
		}
	}

	/**
	 * Creates a topic if it does not already exist
	 * @param name name of the topic
	 * @throws JMSException
	 */
	public void createTopic(String name) throws JMSException {
		if (!admin.destinationExists(name)) {
			if (!admin.addDestination(name, false)) {
				System.err.println("Failed to create topic " + name);
			}
		}
	}
	/**
	 * Closes the administrator connection
	 */
	public void close() {
		admin.close();
	}

}
