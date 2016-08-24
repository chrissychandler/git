package edu.georgiasouthern.math.jgraph;

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
}

