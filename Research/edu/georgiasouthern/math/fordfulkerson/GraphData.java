package edu.georgiasouthern.math.fordfulkerson;

public class GraphData {
	
	//graph 1
	public static final Graph num1 = new Graph();
	static {
		num1.source = 1;
		num1.coords = "1,41,131,2,142,244,3,196,67,4,359,194,5,310,42,6,455,89";
		num1.sink = 6;
		num1.numOfNodes = 6;
		num1.matrix = new int[][] {
			{},
			{0, 0, 16, 13, 0, 0, 0},
			{0, 0, 0, 10, 12, 0, 0},
			{0, 0, 4, 0, 0, 14, 0},
			{0, 0, 0, 9, 0, 0, 20},
			{0, 0, 0, 0, 7, 0, 4},
			{0, 0, 0, 0, 0, 0, 0},
		};	
		num1.numOfStages = 1;
		num1.z = new float[][] {
			{1,1,1,1,1,1},
			{1,1,1,1,1,1},
			{1,1,1,1,1,1},
		};
	}
	
	
	
	public static int[][] computeMatrix(Graph graphData) {
		int[][] newmatrix = graphData.matrix;
		int dim = graphData.numOfNodes + (graphData.numOfStages - 1) * 2 * graphData.numOfNodes;
		
		//newmatrix = new int[dim + 1][dim];
		//we need to populate
		
		return newmatrix;
	}

}
