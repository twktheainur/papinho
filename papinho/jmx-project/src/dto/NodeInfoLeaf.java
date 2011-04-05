package dto;

import java.io.Serializable;

public class NodeInfoLeaf implements Serializable,NodeInfo {
	private String name;
	private long memory;
	
	public NodeInfoLeaf(String id){
		this.name=id;
	}
	
	public NodeInfoLeaf(String id,long memory){
		this.name=id;
		this.memory=memory;
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
}