package virtualtree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import bootstrap.Main;

public class VirtualNodeLocalExecutionVisitor implements VirtualNodeVisitor {
	private String osPrefix;

	public VirtualNodeLocalExecutionVisitor(String osPrefix) {
		this.osPrefix = osPrefix;

	}

	String getOsPrefix() {
		return osPrefix;
	}

	void setOsPrefix(String prefix) {
		osPrefix = prefix;
	}

	public void visit(VirtualNode node) {
		List<String> args = new ArrayList<String>();
		/*args.add("java");
		args.add("-jar");
		args.add("dismon.jar");
		args.add("--uri");
		args.add(Main.URI);
		args.add("-p");
		args.add(node.getId() + "Topic");*/
		String cmd = getOsPrefix()+ " " + "java -jar dismon.jar --uri " + Main.URI +" -m "+node.getId() + "Topic "; 
		if(node.getParent()!=null){
			cmd +=" -p " + node.getParent().getId() + "Topic ";
		}
		for (VirtualNode c : node.getChildren()) {
			 cmd += " " + c.getId() + "Topic";
			//args.add(c.getId() + "Topic");
		}
		System.out.println(cmd);
		try {
			String s= null;
			/*String[] aa = new String[args.size()];
			int i = 0;
			for (String arg : args) {
				aa[i] = arg;
				i++;
			}*/
			Process p = Runtime.getRuntime().exec(cmd);

			// run the Unix "ps -ef" command
			// using the Runtime exec method:

			/*BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(
					p.getErrorStream()));

			// read the output from the command
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			// read any errors from the attempted command
			while ((s = stdError.readLine()) != null) {
				System.err.println(s);
			}*/

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {

		}
		// Runtime.getRuntime().exec();
		/*
		 * if (node.getChildren().isEmpty()) { System.out.println("Child"); }
		 * else { System.out.println("Parent"); }
		 */
	}
}
