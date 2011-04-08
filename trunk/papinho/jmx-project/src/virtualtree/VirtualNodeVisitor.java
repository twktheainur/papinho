package virtualtree;

/**
 * "Abstract" visitor interface
 *
 */
public interface VirtualNodeVisitor {
	/**
	 * Abstract method executed for each node
	 * @param node <code>VirtualNode</code> visited
	 */
	public void visit(VirtualNode node);
}
