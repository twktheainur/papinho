package virtualtree;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TemporaryTopic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;

import bootstrap.Bootstrap;

import utils.JMSAdminServer;

public class ChannelCreatorVisitor implements VirtualNodeVisitor {
	public void visit(VirtualNode node) {
		String topicName = node.getId() + "Topic";
		try{
			Bootstrap.adminServer.createTopic(topicName);
		} catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}
}
