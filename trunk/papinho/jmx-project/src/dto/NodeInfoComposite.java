package dto;

import java.util.ArrayList;
import java.util.List;

public class NodeInfoComposite implements NodeInfo{
	
	private List<NodeInfo> components;
	
	public NodeInfoComposite() {
		components = new ArrayList<NodeInfo>();
	}
	
	public void addNodeInfo(NodeInfo ni){
		components.add(ni);
	}
	
	public void clear(){
		components.clear();
	}
	
	@Override
	public long getMemory() {
		long avg = 0;
		for(NodeInfo n:components){
			avg += n.getMemory();
		}
		return avg;
	}
}
