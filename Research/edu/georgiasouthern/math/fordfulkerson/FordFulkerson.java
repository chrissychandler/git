package edu.georgiasouthern.math.fordfulkerson;
import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import edu.georgiasouthern.math.jgraph.GraphFrame;
import edu.georgiasouthern.math.jgraph.GraphUtilities;
 
public class FordFulkerson
{
    private int[] parent;
    private Queue<Integer> queue;
    private int numberOfVertices;
    private boolean[] visited;
    private int[][] residualGraph;
 
    public FordFulkerson(int numberOfVertices)
    {
        this.numberOfVertices = numberOfVertices;
        this.queue = new LinkedList<Integer>();
        parent = new int[numberOfVertices + 1];
        visited = new boolean[numberOfVertices + 1];		
    }
 
    public boolean bfs(int source, int goal, int graph[][])
    {
        boolean pathFound = false;
        int destination, element;
 
        for(int vertex = 1; vertex <= numberOfVertices; vertex++)
        {
            parent[vertex] = -1;
            visited[vertex] = false;
        }
 
        queue.add(source);
        parent[source] = -1;
        visited[source] = true;
 
        while (!queue.isEmpty())
        { 
            element = queue.remove();
            destination = 1;
 
            while (destination <= numberOfVertices)
            {
                if (graph[element][destination] > 0 &&  !visited[destination])
                {
                    parent[destination] = element;
                    queue.add(destination);
                    visited[destination] = true;
                }
                destination++;
            }
        }
        
        
        if(visited[goal])
        {
            pathFound = true;
        }
        
        return pathFound;
    }
 
    public int fordFulkerson(int graph[][], int source, int destination)
    {
        int u, v;
        int maxFlow = 0;
        int pathFlow;
 
        residualGraph = new int[numberOfVertices + 1][numberOfVertices + 1];
        for (int sourceVertex = 1; sourceVertex <= numberOfVertices; sourceVertex++)
        {
            for (int destinationVertex = 1; destinationVertex <= numberOfVertices; destinationVertex++)
            {
                residualGraph[sourceVertex][destinationVertex] = graph[sourceVertex][destinationVertex];
            }
        }
 
        while (bfs(source ,destination, residualGraph))
        {
            pathFlow = Integer.MAX_VALUE;
            for (v = destination; v != source; v = parent[v])
            {
                u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }
            for (v = destination; v != source; v = parent[v])
            {
                u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }
            maxFlow += pathFlow;	
        }
 
        //int[] minCut = new int[];
        //boolean[] minCut = new boolean[visited.length];
        Set<Point> minCut = new HashSet<Point>();
        
        for(v = 0; v <= numberOfVertices; v++) {
        	for(u = 0; u <=  numberOfVertices; u++) {
        		if (visited[v]==true && visited[u]==false && graph[v][u] > 0) {
        			minCut.add(new Point(v, u));
        		}
        	}
        }
        
        //output the min cut
        Iterator<Point> it = minCut.iterator();
        while (it.hasNext()) {
        	Point p = it.next();
        	System.out.println("(v, u) = (" + ((int) p.getX()) + ", " + ((int) p.getY()) + ")");
        }

        return maxFlow;
    }
    
    
 
    public int[][] getResidualGraph() {
		return residualGraph;
	}

	public static void main(String...arg)
    {
        int[][] graph;
        int numberOfNodes;
        int source;
        int sink;
        int maxFlow;
        
        graph = GraphData.num1.matrix;
        numberOfNodes = GraphData.num1.numOfNodes;
        source = GraphData.num1.source;
        sink = GraphData.num1.sink;
        
        int[][] newGraph = new int[graph.length * 2][graph.length * 2];
		
		for(int i = 0; i < graph.length; i++) {
        	for (int j = 0; j < graph[i].length; j++) {	
        		newGraph[i][j] = graph[i][j];
        	}
        }
        int w = graph.length;
		for(int i = 0; i < graph.length; i++) {
        	
			for (int j = 0; j < graph[i].length; j++) {
        		newGraph[i+w][j+w] = graph[i][j];	
        	}
        }
        
 
        /*Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of nodes");
        numberOfNodes = scanner.nextInt();
        graph = new int[numberOfNodes + 1][numberOfNodes + 1];
 
        System.out.println("Enter the graph matrix");
        for (int sourceVertex = 1; sourceVertex <= numberOfNodes; sourceVertex++)
        {
           for (int destinationVertex = 1; destinationVertex <= numberOfNodes; destinationVertex++)
           {
               graph[sourceVertex][destinationVertex] = scanner.nextInt();
           }
        }
 
        System.out.println("Enter the source of the graph");
        source= scanner.nextInt();
 
        System.out.println("Enter the sink of the graph");
        sink = scanner.nextInt();*/
        
        GraphFrame frame = GraphFrame.showFrame();

		//display a new graph
		GraphUtilities.createNewGraph(frame.getGraphPanel(), graph);
		GraphUtilities.labelGraphEdges(frame.getGraphPanel(), graph);
		frame.getGraphPanel().layoutGraph3();
 
        FordFulkerson fordFulkerson = new FordFulkerson(numberOfNodes);
        maxFlow = fordFulkerson.fordFulkerson(graph, source, sink);
        System.out.println("The Max Flow is " + maxFlow);
        //scanner.close();
        
        GraphFrame frame2 = GraphFrame.showFrame();
        GraphUtilities.createNewGraph(frame2.getGraphPanel(), fordFulkerson.getResidualGraph());
		GraphUtilities.labelGraphEdges(frame2.getGraphPanel(), fordFulkerson.getResidualGraph());
		frame2.getGraphPanel().layoutGraph3();
		
		GraphFrame frame3 = GraphFrame.showFrame();
		GraphUtilities.createNewGraph(frame3.getGraphPanel(), newGraph);
		GraphUtilities.labelGraphEdges(frame3.getGraphPanel(), newGraph);
		frame3.getGraphPanel().layoutGraph3();
    }
}