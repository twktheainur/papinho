package virtualtree;

import java.util.Set;

public class VirtualNode {
	private VirtualNode parent;
	private Set<VirtualNode> children;
	
	public VirtualNode(int n, VirtualNode parent, String host) {
		super();
		this.parent = parent;
	}
	public VirtualNode getParent() {
		return parent;
	}
	public void setParent(VirtualNode parent) {
		this.parent = parent;
	}
	public Set<VirtualNode> getChildren() {
		return children;
	}
	public void setChildren(Set<VirtualNode> children) {
		this.children = children;
	}
	public void addChild(VirtualNode vn){
		children.add(vn);
	}
}
