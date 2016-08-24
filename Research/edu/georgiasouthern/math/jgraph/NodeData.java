/**
 * 
 */
package edu.georgiasouthern.math.jgraph;

/**
 * Holds graph vertex information.
 * 
 * @author Emil Iacob
 *
 */
public class NodeData {
	private String id;
	private String descriptor;
	/**
	 * Creates vertex info.
	 * @param id
	 * @param descriptor
	 */
	public NodeData(String id, String descriptor) {
		this.id = id;
		this.descriptor = descriptor;
	}
	/**
	 * @return the descriptor
	 */
	public String getDescriptor() {
		return descriptor;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * The String representation of the vertex.
	 */
	public String toString() {
		return id;
	}
}
