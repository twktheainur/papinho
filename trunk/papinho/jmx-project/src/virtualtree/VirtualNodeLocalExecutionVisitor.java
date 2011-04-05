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
		String cmd = getOsPrefix()+ " " + "java -jar dismon.jar --uri " + Main.URI +" -m "+node.getId() + "Topic "; 
		if(node.getParent()!=null){
			cmd +=" -p " + node.getParent().getId() + "Topic ";
		}
		for (VirtualNode c : node.getChildren()) {
			 cmd += " " + c.getId() + "Topic";
		}
		System.out.println(cmd);
		try {
			Process p = Runtime.getRuntime().exec(cmd);

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
