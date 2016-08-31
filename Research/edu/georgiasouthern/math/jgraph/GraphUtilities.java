package edu.georgiasouthern.math.jgraph;

import java.awt.geom.Point2D;

import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

public class GraphUtilities {

	public GraphUtilities() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Creates a graph out of an adjacency matrix.
	 * @param graph
	 */
	public static void createNewGraph(GraphPanel graphPanel, int[][] matrix) {
		graphPanel.clearPanel();
		
		//create vertices
		for (int i = 1; i < matrix.length; i++) {
			String label = "" + i;
			graphPanel.addVertex(label);
		}
		
		//create edges
		for (int i = 1; i < matrix.length; i++) {
			for (int j = 1; j < matrix[i].length; j++) {
				if (matrix[i][j] <= 0) {
					continue;
				}
				
				String s = "" + i;
				String t = "" + j;
				graphPanel.addEdge(s, t);
			}
		}
	}

	/**
	 * Adds labels to the graph edges (clears the previous, if any).
	 * @param graphPanel
	 * @param matrix
	 */
	public static void labelGraphEdges(GraphPanel graphPanel, int[][] matrix) {
		//create labels
		for (int i = 1; i < matrix.length; i++) {
			for (int j = 1; j < matrix[i].length; j++) {
				if (matrix[i][j] <= 0) {
					continue;
				}
				
				String s = "" + i;
				String t = "" + j;
				graphPanel.addEdgeLabel(s, t, "" + matrix[i][j]);
			}
		}
	}
	/**
	 * Sets the thickness edge from s->t to w.
	 * @param graphPanel
	 * @param s
	 * @param t
	 * @param w
	 */
	public static void setEdgeThickness(GraphPanel graphPanel, int s, int t, int w) {
		graphPanel.setEdgeThickness("" + s, "" + t, w);
	}
	/**
	 * Sets positions for the graph nodes.
	 * @param graphPanel
	 * @param coords comma separated list of coordinates
	 */
	public static void setNodesPositions(GraphPanel graphPanel, String coords) {
		String[] pos = coords.split(",");
		for (int i = 0; i < pos.length; i += 3) {
			String n = pos[i];
			int x = Integer.valueOf(pos[i+1].trim()).intValue();
			int y = Integer.valueOf(pos[i+2].trim()).intValue();
			graphPanel.setNodePosition(n, x, y);
		}
		
	}
	/**
	 * Returns a list of comma separated values for the coordinates of the nodes.
	 * @param graphPanel
	 * @return
	 */
	public static String getNodesPositions(GraphPanel graphPanel) {
		StringBuilder buf = new StringBuilder();
		Object[] list = graphPanel.getVertices();
		for (int i = 0; i < list.length; i++) {
			Object v = list[i];
			if (v instanceof DefaultGraphCell) {
				DefaultGraphCell cell = (DefaultGraphCell) v;
				NodeData nd = (NodeData) cell.getUserObject();
				String n = nd.getDescriptor();
				Point2D p = graphPanel.getNodePosition(n);
				if (p == null) {
					continue;
				}
				buf.append(nd.getDescriptor()).append(",").append((int) p.getX()).append(",").append((int) p.getY()).append(",");
			}
		}
		
		return buf.toString();
	}
}

