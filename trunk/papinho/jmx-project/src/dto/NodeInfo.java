package dto;

import java.io.Serializable;
/**
 * "Abstract" interface for the composite pattern used for the aggregation of the node information
 *
 */
public interface NodeInfo extends Serializable{
	/**
	 * Abstract getter for the used memory value for the whole tree structure
	 * @return
	 */
	public long getMemory();
}
