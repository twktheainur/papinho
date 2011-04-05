package virtualtree;

import java.util.ArrayList;
import java.util.List;

public class VirtualNode {
	private VirtualNode parent;
	private List<VirtualNode> children;
	private String host;
	private String id;
	public static Integer N;
	private List<String> hosts;

	public VirtualNode(VirtualNode parent, int a, List<String> hosts) {
		children = new ArrayList<VirtualNode>(a);
		this.hosts = hosts;
		parent = null;
	}

	public void buildTree(VirtualNode node, int a, List<String> ids,
			List<String> hosts) {
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

	public Integer getN() {
		return N;
	}

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
