package edu.georgiasouthern.math.fordfulkerson;

import edu.georgiasouthern.math.jgraph.GraphFrame;
import edu.georgiasouthern.math.jgraph.GraphUtilities;

public class Test {
	
	private static int[][] graph1 = new int[][] {
		{0, 16, 13, 0, 0, 0},
		{0, 0, 10, 12, 0, 0},
		{0, 4, 0, 0, 14, 0},
		{0, 0, 9, 0, 0, 20},
		{0, 0, 0, 7, 0, 4},
		{0, 0, 0, 0, 0, 0},
	};

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		GraphFrame frame = GraphFrame.showFrame();

		//display a new graph
		GraphUtilities.createNewGraph(frame.getGraphPanel(), graph1);
		GraphUtilities.labelGraphEdges(frame.getGraphPanel(), graph1);
		GraphUtilities.setEdgeThickness(frame.getGraphPanel(), 1, 2, 4);
		frame.getGraphPanel().layoutGraph3();
	}

}
