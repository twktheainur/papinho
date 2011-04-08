package dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite node for the node information
 *
 */
public class NodeInfoComposite implements NodeInfo{
	
	private List<NodeInfo> components;
	/**
	 * Constructor
	 */
	public NodeInfoComposite() {
		components = new ArrayList<NodeInfo>();
	}
	/**
	 * Add a component 
	 * @param ni a component
	 */
	public void addNodeInfo(NodeInfo ni){
		components.add(ni);
	}
	/**
	 * Purge the components
	 */
	public void clear(){
		components.clear();
	}
	
	@Override
	/**
	 * Get the sum of the memory usage of the components
	 */
	public long getMemory() {
		long avg = 0;
		for(NodeInfo n:components){
			avg += n.getMemory();
		}
		return avg;
	}
}
