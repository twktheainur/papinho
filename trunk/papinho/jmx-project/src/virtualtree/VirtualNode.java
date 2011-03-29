package virtualtree;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
