/**
 * 
 */
package bootstrap;

import java.util.List;

import utils.JMSAdminServer;
import virtualtree.ChannelCreatorVisitor;
import virtualtree.VirtualNode;
import virtualtree.VirtualNodeLocalExecutionVisitor;

import DistMon.DistributedMonitor;


/**
 * @author twk
 * 
 */
public class Bootstrap {

	public static JMSAdminServer adminServer;
	
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
		/*
		VirtualNode.N = N;
		VirtualNode vn = new VirtualNode(null,arity,hosts);
		vn.setId("node0");
		vn.buildTree(vn, arity,null,hosts);
		*/
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
		
		//return vn;
		return tree[0];
	}
	
	/**
	 * @param args
	 */
	public static void bootstrap(String jmsURI,String username,String password,int N, int V, boolean isArity ,String[] hosts) {
		adminServer = new JMSAdminServer(jmsURI, username, password);
		VirtualNode tree = Bootstrap.buildTree(N, V, isArity, null);
		ChannelCreatorVisitor cv = new ChannelCreatorVisitor();
		tree.accept(cv);
		VirtualNodeLocalExecutionVisitor vnlev = new VirtualNodeLocalExecutionVisitor("cmd /c start");
		tree.accept(vnlev);
	}
}
