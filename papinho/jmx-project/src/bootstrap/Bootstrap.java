/**
 * 
 */
package bootstrap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import utils.JMSAdminServer;
import virtualtree.ChannelCreatorVisitor;
import virtualtree.VirtualNode;
import virtualtree.VirtualNodeLocalExecutionVisitor;

import DistMon.DistributedMonitor;

import com.sun.corba.se.impl.encoding.CodeSetConversion.BTCConverter;

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
		return new VirtualNode(null, N,depth, arity,0, hosts);
	}
	
	/**
	 * @param args
	 */
	public static void bootstrap(String jmsURI,String username,String password,int N, int V, boolean isArity ,String[] hosts) {
		adminServer = new JMSAdminServer(jmsURI, username, password);
		VirtualNode tree = Bootstrap.buildTree(N, V, isArity, null);
		ChannelCreatorVisitor cv = new ChannelCreatorVisitor();
		tree.accept(cv);
		VirtualNodeLocalExecutionVisitor vnlev = new VirtualNodeLocalExecutionVisitor();
		tree.accept(vnlev);
		
		
	}
}
