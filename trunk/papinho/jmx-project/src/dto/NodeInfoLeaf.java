package dto;

import java.io.Serializable;

/**
 *  Concrete class for the leaf of the composite pattern holding the node information
 */
@SuppressWarnings("serial")
public class NodeInfoLeaf implements Serializable,NodeInfo {
	private String name;
	private long memory;
	private double cpuLoad;

	/**
	 * Cosntructor 
	 * @param id string id of the node the information comes from
	 */
	public NodeInfoLeaf(String id){
		this.name=id;
	}
	
	/**
	 * Cosntructor 
	 * @param id string id of the node the information comes from
	 * @param memory used memory on the node
	 * @param cpuLoad cpu load on the node
	 */
	public NodeInfoLeaf(String id,long memory,double cpuLoad){
		this.name=id;
		this.memory=memory;
		this.cpuLoad=cpuLoad;
	}
	
	/**
	 * Get the name of the node the information is about
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public long getMemory() {
		return memory;
	}
	public void setMemory(long memory) {
		this.memory = memory;
	}	
	
	public double getCpuLoad() {
		return cpuLoad;
	}

	public void setCpuLoad(double cpuLoad) {
		this.cpuLoad = cpuLoad;
	}
	
}