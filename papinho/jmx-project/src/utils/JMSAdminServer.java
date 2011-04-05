package utils;

import java.util.Iterator;
import java.util.Vector;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.naming.Context;
import org.exolab.jms.administration.AdminConnectionFactory;
import org.exolab.jms.administration.JmsAdminServerIfc;

public class JMSAdminServer {

	JmsAdminServerIfc admin;
	Context context;

	public JMSAdminServer(String url, String username, String password) {
		/*
		 * Hashtable<String,String> properties = new Hashtable<String,String>();
		 * properties.put(Context.INITIAL_CONTEXT_FACTORY,
		 * "org.exolab.jms.jndi.InitialContextFactory");
		 * properties.put(Context.PROVIDER_URL, url); try{ context = new
		 * InitialContext(properties); } catch (Exception e){
		 * e.printStackTrace(); }
		 */
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

	public void createTopic(String name) throws JMSException {
		if (!admin.destinationExists(name)) {
			if (!admin.addDestination(name, false)) {
				System.err.println("Failed to create topic " + name);
			}
		}
	}

	public void close() {
		admin.close();
	}

}
