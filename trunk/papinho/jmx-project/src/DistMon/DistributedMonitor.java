package DistMon;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DistributedMonitor {

	String parent;
	List<String> children;
	TopicSession parentTopicSession = null;
	TopicPublisher parentTopicPublisher = null;
	TopicConnectionFactory topicConnectionFactory = null;
	TopicConnection topicConnection = null;
	Topic topic = null;
	Context context = null;
	Context jndiContext = null;
	
	public void initConnection(String topicName){
		


		Hashtable properties = new Hashtable();
		properties.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.exolab.jms.jndi.InitialContextFactory");
		properties.put(Context.PROVIDER_URL, "rmi://localhost:1099/");

		
		try {
			context = new InitialContext(properties);
		} catch (Exception e) {
			System.out.println("Initial context error");

		}
		
		try {
			jndiContext = new InitialContext();
		} catch (NamingException e) {
			System.out.println("Could not create JNDI API " + "context: "
					+ e.toString());
			e.printStackTrace();
			System.exit(1);
		}
		
		try {
			topicConnectionFactory = (TopicConnectionFactory) context
					.lookup("JmsTopicConnectionFactory");

			try {
				TopicConnection d = topicConnectionFactory
						.createTopicConnection();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		} catch (NamingException e) {
			System.out.println("JNDI API lookup failed: " + e.toString());
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	public DistributedMonitor(String parent, List<String> children) {
		this.parent = parent;
		this.children = children;
		
		initConnection("T1");
		
		for(String i: children){
			dispatchForParent(parent,"message "+i);		
		}

	}

	private void Subscriber(){
		
//		try {
//            TopicSub topicSubscriber = 
//                parentTopicSession.createSubscriber(topic);
//            topicListener = new TextListener();
//            topicSubscriber.setMessageListener(topicListener);
//            topicConnection.start();
//            System.out.println("To end program, enter Q or q, " +
//                "then <return>");
//            inputStreamReader = new InputStreamReader(System.in);
//            while (!((answer == 'q') || (answer == 'Q'))) {
//                try {
//                    answer = (char) inputStreamReader.read();
//                } catch (IOException e) {
//                    System.out.println("I/O exception: " 
//                        + e.toString());
//                }
//            }
//        } catch (JMSException e) {
//            System.out.println("Exception occurred: " +
//                e.toString());
//        } finally {
//            if (topicConnection != null) {
//                try {
//                    topicConnection.close();
//                } catch (JMSException e) {}
//            }
////        }
		
	}
	
	private void dispatchForParent(String destination, String strmessage) {

		//topic = (Topic) context.lookup(destination);
		
		TextMessage message = null;
		final int NUM_MSGS;

		System.out.println("Topic name is " + destination);



		/*
		 * Create connection. Create session from connection; false means
		 * session is not transacted. Create publisher and text message. Send
		 * messages, varying text slightly. Finally, close connection.
		 */
		try {
			topicConnection = topicConnectionFactory.createTopicConnection();
			parentTopicSession = topicConnection.createTopicSession(false,
					Session.AUTO_ACKNOWLEDGE);
			parentTopicPublisher = parentTopicSession.createPublisher(topic);
			message = parentTopicSession.createTextMessage();
			//message.setText("This is message mine");
			message.setText(strmessage);
			System.out.println("Publishing message: " + message.getText());
			parentTopicPublisher.publish(message);
		} catch (JMSException e) {
			System.out.println("Exception occurred: " + e.toString());
		} finally {
			if (topicConnection != null) {
				try {
					topicConnection.close();
				} catch (JMSException e) {
				}
			}
		}

	}
	
	public static void main(String[] args) {
		List<String> mychildren = new ArrayList();
		mychildren.add("T2");
		mychildren.add("T3");
		DistributedMonitor root = new DistributedMonitor("T1", mychildren);
	}

}
