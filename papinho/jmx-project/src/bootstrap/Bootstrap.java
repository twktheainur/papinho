/**
 * 
 */
package bootstrap;

import java.io.IOException;
import java.util.List;

import utils.JMSAdminServer;
import virtualtree.ChannelCreatorVisitor;
import virtualtree.VirtualNode;
import virtualtree.VirtualNodeLocalExecutionVisitor;

import DistMon.DistributedMonitor;


/**
 * Bootstrap class to create abstract tree, topics,
 * and run a JVM for each abstract node
 *
 */
public class Bootstrap {

	/** JMSAdminServer object to create a connection factory */
	public static JMSAdminServer adminServer;
	
	/**  
	 * Build an abstract tree of N elements with given arity/depth
	 * 
	 * @param N the number of nodes
	 * @param value the arity or depth of tree
	 * @param useArity consider argument 'value' as arity or as depth
	 * @param hosts the list of hosts which represents the tree
	 * 
	 * @return the root of an abstract tree
	 */
	public static VirtualNode buildTree(int N, int value, boolean useArity, List<String> hosts){
		int arity = -1;
		int depth = -1;
		if(useArity){
			arity = value;
			depth = (int)(Math.log(N)/Math.log(arity));
		} else {
			depth = value;
			int a = 2;
			for(a=2;(int)Math.pow(a, depth)<=N;a++); //computing arity knowing N and depth
			arity = a;
			System.out.println(a);
		}
		System.out.println("depth = " + depth);
		
		//---------------------CREATING AN ABSTRACT TREE----------------------
		VirtualNode[] tree;
		tree = new VirtualNode[N];
		//special case for the root
		tree[0] = new VirtualNode(null,arity,hosts);
		tree[0].setId("node0");
		for (int i = 1; i < arity+1; i++) {
			tree[i] = new VirtualNode(tree[0],arity,hosts);
			tree[i].setId("node" + i);
			tree[0].addChild(tree[i]);
		}
		//for other nodes
		for(int i = 1; i < N; i++) {
			
			//nodes that have #arity children
			if( (i+1)*arity < N ) {
				for (int j = i*arity + 1; j <= i*arity + arity; j++) {
					tree[j] = new VirtualNode(tree[i],arity,hosts);
					tree[j].setId("node" + j);
					tree[i].addChild(tree[j]);
				}
			}
						
			//nodes that have less than #arity children
			else if ( i == (int)((N-1)/arity) ) {
				System.out.println("N = " + N);
				for (int j = i*arity + 1; j < N; j++) {
					tree[j] = new VirtualNode(tree[i],arity,hosts);
					tree[j].setId("node" + j);
					tree[i].addChild(tree[j]);
				}
			}
		}
		
		return tree[0];
	}
	
	/**
	 * Bootstraps the distributed application.
	 * 
	 * 1. Creates a connection factory
	 * 2. Builds an abstract tree
	 * 3. Creates a topic for each node of the tree
	 * 4. Runs JVM for each node of the abstract tree
	 * 
	 * @param jmsURI the address of JMS server
	 * @param username name of the user
	 * @param password user's password
	 * @param N number of nodes for an abstract tree
	 * @param V arity/depth of an abstract tree
	 * @param isArity specifies if arity or depth is provided
	 * @param hosts the list of hosts which represents the tree
	 */
	public static void bootstrap(String jmsURI, String username, String password, 
			int N, int V, boolean isArity ,String[] hosts) {
		
		adminServer = new JMSAdminServer(jmsURI, username, password);
		
		VirtualNode tree = Bootstrap.buildTree(N, V, isArity, null);
		
		ChannelCreatorVisitor cv = new ChannelCreatorVisitor();
		tree.accept(cv);
		
		String OS=System.getProperty("os.name");
		
		VirtualNodeLocalExecutionVisitor vnlev;
		if(OS.equals("Linux")){
			vnlev = new VirtualNodeLocalExecutionVisitor("xterm -e ");	
		} else{
			vnlev = new VirtualNodeLocalExecutionVisitor("cmd /c start");
		}
						
		tree.accept(vnlev);
	}
}