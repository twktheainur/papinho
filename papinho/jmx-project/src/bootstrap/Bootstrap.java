/**
 * 
 */
package bootstrap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import virtualtree.VirtualNode;
import virtualtree.VirtualNodeLocalVisitor;

import com.sun.corba.se.impl.encoding.CodeSetConversion.BTCConverter;

/**
 * @author twk
 * 
 */
public class Bootstrap {

	
	public static VirtualNode buildTree(int N, int value, boolean useArity, List<String> hosts){
		int arity = -1;
		int depth = -1;
		if(useArity){
			arity = value;
			depth = (int)(Math.log(N)/Math.log(arity));
		} else {
			depth = value;
			int a = 2;
			for(a=2;(int)Math.pow(a, depth)<=N;a++);
			arity = a;
			System.out.println(a);
		}
		return new VirtualNode(null, N,depth, arity,0, hosts);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean local = false;
		String answer;
		boolean arity = false;
		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(converter);
		Bootstrap bst;
		/*System.out.println("Run locally?");
		try {
			answer = in.readLine();
			if (answer.equals("yes")) {
				local = true;
			}
			System.out.println("What is N?");
			answer = in.readLine();
			int n = Integer.valueOf(answer);
			ArrayList<String> hosts = new ArrayList<String>();
			if (!local) {
				for(int i=0;i<n;i++){
					System.out.println("Host "+i+" :");
					hosts.add(in.readLine());
				}
			} 
			System.out.println("Select: 1/Arity -- 2/Depth:");
			answer = in.readLine();
			if(answer.equals("1")){
				System.out.println("Arity :");
				arity = true;
			} else{
				System.out.println("Depth :");
			}
			int v = Integer.valueOf(in.readLine());
			if(local){
				bst = new Bootstrapper(n, v, arity);				
			} else {
				bst = new Bootstrapper(n, v, arity, hosts);
			}
		}

		catch (Exception e) {
		}*/
		final VirtualNode tree = Bootstrap.buildTree(200, 10, false, null);
		VirtualNodeLocalVisitor lv = new VirtualNodeLocalVisitor();
		tree.accept(lv);

	}
}
