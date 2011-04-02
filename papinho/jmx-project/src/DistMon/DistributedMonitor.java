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
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import example.TextListener;

public class DistributedMonitor {

	String                  topicName = "T1";
    Context                 jndiContext = null;
    TopicConnectionFactory  topicConnectionFactory = null;
    TopicConnection         topicConnection = null;
    TopicSession            topicSession = null;
    Topic                   topic = null;
    TopicSubscriber         topicSubscriber = null;
    TopicPublisher          topicPublisher = null;
    TextListener            topicListener = null;
    TextMessage             message = null;
    InputStreamReader       inputStreamReader = null;
    char                    answer = '\0';
    String parent;
    List<String> children;
	
    public DistributedMonitor(String parent, List<String> children) {
		this.parent = parent;
		this.children = children;
		
		initConnection("T1");
		
		for(String i: children){
			dispatchForParent(parent,"message "+i);		
		}

	}
    
    
	public void initConnection(String topicName){
		


		Hashtable properties = new Hashtable();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, 
                       "org.exolab.jms.jndi.InitialContextFactory");
        properties.put(Context.PROVIDER_URL, "rmi://localhost:1099/");
        
        Context context = null;
        try{
        context = new InitialContext(properties);
        } catch(Exception e){
        	System.out.println("Initial context error");
       
        }
        
        /*
         * Read topic name from command line and display it.
         */
        System.out.println("Topic name is " + topicName);

        /* 
         * Create a JNDI API InitialContext object if none exists
         * yet.
         */
        try {
            jndiContext = new InitialContext();
        } catch (NamingException e) {
            System.out.println("Could not create JNDI API " +
                "context: " + e.toString());
            e.printStackTrace();
            System.exit(1);
        }

        /* 
         * Look up connection factory and topic.  If either does
         * not exist, exit.
         */
        try {
            topicConnectionFactory = (TopicConnectionFactory)
               context.lookup("JmsTopicConnectionFactory");
            topic = (Topic) context.lookup(topicName);
        } catch (NamingException e) {
            System.out.println("JNDI API lookup failed: " +
                e.toString());
            e.printStackTrace();
            System.exit(1);
        }

	}

	private void Subscriber(){
		
		try {
            topicConnection = 
                topicConnectionFactory.createTopicConnection();
            topicSession = 
                topicConnection.createTopicSession(false, 
                    Session.AUTO_ACKNOWLEDGE);
            topicSubscriber = 
                topicSession.createSubscriber(topic);
            topicListener = new example.TextListener();
            topicSubscriber.setMessageListener(topicListener);
            topicConnection.start();
            System.out.println("To end program, enter Q or q, " +
                "then <return>");
            inputStreamReader = new InputStreamReader(System.in);
            while (!((answer == 'q') || (answer == 'Q'))) {
                try {
                    answer = (char) inputStreamReader.read();
                } catch (IOException e) {
                    System.out.println("I/O exception: " 
                        + e.toString());
                }
            }
        } catch (JMSException e) {
            System.out.println("Exception occurred: " +
                e.toString());
        } finally {
            if (topicConnection != null) {
                try {
                    topicConnection.close();
                } catch (JMSException e) {}
            }
        }
    
		
	}
	
	private void dispatchForParent(String destination, String strmessage) {

		try {
            topicConnection = 
                topicConnectionFactory.createTopicConnection();
            topicSession = 
                topicConnection.createTopicSession(false, 
                    Session.AUTO_ACKNOWLEDGE);
            topicPublisher = topicSession.createPublisher(topic);
            message = topicSession.createTextMessage();
            
                message.setText(strmessage);
                System.out.println("Publishing message: " + 
                    message.getText());
                topicPublisher.publish(message);
            
        } catch (JMSException e) {
            System.out.println("Exception occurred: " + 
                e.toString());
        } finally {
            if (topicConnection != null) {
                try {
                    topicConnection.close();
                } catch (JMSException e) {}
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
