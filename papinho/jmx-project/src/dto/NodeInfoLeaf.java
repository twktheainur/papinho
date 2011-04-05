package dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NodeInfoLeaf implements Serializable,NodeInfo {
	private String name;
	private long memory;
	private double cpuLoad;

	public NodeInfoLeaf(String id){
		this.name=id;
	}
	
	public NodeInfoLeaf(String id,long memory,double cpuLoad){
		this.name=id;
		this.memory=memory;
		this.cpuLoad=cpuLoad;
	}
	
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