package virtualtree;

import java.util.ArrayList;
import java.util.List;
/**
 * Node class of the bootstrap vitual tree
 *
 */
public class VirtualNode {
	private VirtualNode parent;
	private List<VirtualNode> children;
	private String host;
	private String id;
	public static Integer N;
	private List<String> hosts;

	/**
	 * Constructor
	 * @param parent Parent node
	 * @param a arity
	 * @param hosts a list of ip addresses/hostnames, can be null if testing locally
	 */
	public VirtualNode(VirtualNode parent, int a, List<String> hosts) {
		children = new ArrayList<VirtualNode>(a);
		this.hosts = hosts;
		//parent = null;
		setParent(parent);
	}
/*
	public void buildTree(VirtualNode node, int a, List<String> ids, List<String> hosts) {
		if (VirtualNode.N > 0) {
			if (ids == null) {
				ids = new ArrayList<String>(VirtualNode.N);
				for (int i = 1; i < VirtualNode.N; i++) {
					ids.add("node" + i);
				}
			}
			node.setId(ids.remove(0));
			if (hosts != null) {
				node.setHost(hosts.remove(0));
			}
			for (int i = 0; i < a && VirtualNode.N > 0; i++) {
				VirtualNode.N--;
				node.addChild(new VirtualNode(this, a, hosts));
			}
			int size = node.children.size();
			for (int i = 0; i < size; i++) {
				VirtualNode child = node.getChildren().get(i);
				child.setParent(node);
				buildTree(child, a, ids, hosts);
			}
		}
	}
*/
	/**
	 * Get the number of nodes
	 */
	public Integer getN() {
		return N;
	}

	/**
	 * Set the total number of nodes
	 * @param n
	 */
	public void setN(Integer n) {
		N = n;
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
	
	/**
	 * Implementation of the visitor pattern, calls the accept method of each child and then
	 * the visit method for the current node. This performs a Depth First Exploration
	 * @param v
	 */
	public void accept(VirtualNodeVisitor v) {
		for (VirtualNode c : getChildren()) {
			c.accept(v);
		}
		v.visit(this);
	}

	/**
	 * Returns the string id of the node
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the string id of the node
	 * @param id string id of the node
	 */
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
