package virtualtree;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class VirtualNode {
	private VirtualNode parent;
	private List<VirtualNode> children;
	private String host;
	private String id;

	public VirtualNode(VirtualNode parent, int N, int D,int a, int id, List<String> hosts) {
		this.parent = parent;
		this.id = "node"+String.valueOf(id);
		children = new ArrayList<VirtualNode>(a);
		if (N > 0 && D>0) {
			if (hosts != null) {
				host = hosts.remove(0);
			}
			for (int i = 0; i < a && N != 0; i++) {
				N--;
				addChild(new VirtualNode(this, N,D-1, a,++id, hosts));
			}
		}
		
		
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
		
		 try {
	            TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory)
	                context.lookup("JmsTopicConnectionFactory");
	            
	            try {
					TopicConnection d=topicConnectionFactory.createTopicConnection();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	            
	            Topic topic = (Topic) context.lookup(parent.getId()+"Topic");
	        } catch (NamingException e) {
	            System.out.println("JNDI API lookup failed: " +
	                e.toString());
	            e.printStackTrace();
	            System.exit(1);
	        }
		
	}

	public VirtualNode getParent() {
		
		return parent;
	}

	public void setParent(VirtualNode parent) {
		this.parent = parent;
	}

	public List<VirtualNode> getChildren() {
		return children;
	}

	public void setChildren(List<VirtualNode> children) {
		this.children = children;
	}

	public void addChild(VirtualNode vn) {
		children.add(vn);
	}

	public void accept(VirtualNodeVisitor v) {
		for (VirtualNode c : getChildren()) {
			c.accept(v);
		}
		v.visit(this);
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
