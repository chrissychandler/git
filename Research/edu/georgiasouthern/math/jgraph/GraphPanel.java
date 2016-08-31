/**
 * 
 */
package edu.georgiasouthern.math.jgraph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.JGraphLayout;
import com.jgraph.layout.graph.JGraphSimpleLayout;
import com.jgraph.layout.organic.JGraphFastOrganicLayout;
import com.jgraph.layout.tree.JGraphTreeLayout;

/**
 * Defines a wrapper for a graph panel.
 * 
 * 
 * @author Emil Iacob
 *
 */
public class GraphPanel extends JPanel {
	/**
	 * Holds the JGraph instance.
	 */
	private JGraph jgraph;
	private DefaultGraphCell lastVisitedVertex = null;
	/**
	 * Creates the graph panel.
	 *
	 */
	public GraphPanel() {
		initGraph();
		populateContentPane();
	}
	/**
	 * 
	 */
	public void clearPanel() {
		jgraph.removeAll();
	}
	/**
	 * Arranges the nodes neatly.
	 */
	public void layoutGraph1() {
		JGraphFacade facade = new JGraphFacade(jgraph); // Pass the facade 	the JGraph instance
		JGraphLayout layout = new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_CIRCLE); // Create an instance of the circle layout
		layout.run(facade); // Run the layout on the facade.
		Map nested = facade.createNestedMap(true, true); // Obtain a map of the resulting attribute changes from the facade
		jgraph.getGraphLayoutCache().edit(nested); // Apply the results to the actual graph
	}
	
	public void layoutGraph2() {
			JGraphFacade facade = new JGraphFacade(jgraph); // Pass the facade the JGraph instance
			JGraphLayout layout = new JGraphFastOrganicLayout(); // Create an instance of the appropriate layout
			layout.run(facade); // Run the layout on the facade. Note that layouts do not implement the Runnable interface, to avoid confusion
			Map nested = facade.createNestedMap(true, true); // Obtain a map of the resulting attribute changes from the facade
			jgraph.getGraphLayoutCache().edit(nested); // Apply the results to the actual graph
	}
	
	public void layoutGraph3() {
		DefaultGraphCell s = findGraphCell("1");
		Object[] roots = new Object[] { s }; // replace getRoots with your own Object array of the cell tree roots. NOTE: these are the root cell(s) of the tree(s), not the roots of the graph model.
		JGraphFacade facade = new JGraphFacade(jgraph, roots); // Pass the facade the JGraph instance
		JGraphTreeLayout layout = new JGraphTreeLayout(); // Create an instance of the appropriate layout
		layout.setOrientation(SwingConstants.WEST);
		layout.run(facade); // Run the layout on the facade.
		Map nested = facade.createNestedMap(true, true); // Obtain a map of the resulting attribute changes from the facade
		jgraph.getGraphLayoutCache().edit(nested); // Apply the results to the actual graph
	}
	/**
	 * Adds a new vertex if not already in the graph.
	 * @param descriptor
	 */
	public void addVertex(String descriptor) {
		//descriptor = descriptor.substring(descriptor.indexOf(" ")).trim();
		DefaultGraphCell newvertex = findGraphCell(descriptor);
		if (newvertex == null) {//create a new vertex
			newvertex = createNode(descriptor);
		}
	}
	/**
	 * Creates a new graph edge.
	 * @param from
	 * @param to
	 * @return
	 */
	public boolean addEdge(String from, String to) {
		DefaultGraphCell s = findGraphCell(from);
		if (s == null) {
			return false;
		}
		DefaultGraphCell t = findGraphCell(to);
		if (t == null) {
			return false;
		}
		createEdge(s, t);
		
		return true;
	}
	/**
	 * Adds the label to an edge. Returns <code>false</code> if edge not found.
	 * @param from
	 * @param to
	 * @param label
	 */
	public boolean addEdgeLabel(String from, String to, String label) {
		DefaultGraphCell s = findGraphCell(from);
		if (s == null) {
			return false;
		}
		DefaultGraphCell t = findGraphCell(to);
		if (t == null) {
			return false;
		}
		Object[] edges = DefaultGraphModel.getEdgesBetween(jgraph.getModel(), s, t, false);
		if (edges == null || edges.length == 0) {
			return false;
		}
		DefaultEdge edge = (DefaultEdge) edges[0];
		edge.setUserObject(label);
		
		return true;
	}
	/**
	 * Returns the edge label or null if not found.
	 * @param from
	 * @param to
	 * @return
	 */
	public String getEdgeLabel(String from , String to) {
		DefaultGraphCell s = findGraphCell(from);
		if (s == null) {
			return null;
		}
		DefaultGraphCell t = findGraphCell(to);
		if (t == null) {
			return null;
		}
		Object[] edges = DefaultGraphModel.getEdgesBetween(jgraph.getModel(), s, t, false);
		if (edges == null || edges.length == 0) {
			return null;
		}
		DefaultEdge edge = (DefaultEdge) edges[0];
		Object label = edge.getUserObject();
		if (label == null) {
			label = "";
		}
		
		return label.toString();
		
	}
	/**
	 * Sets an edge thickness.
	 * @param from
	 * @param to
	 * @param w
	 * @return
	 */
	public boolean setEdgeThickness(String from, String to, int w) {
		if (w <= 0 || w > 10) {
			return false;
		}
		DefaultGraphCell s = findGraphCell(from);
		if (s == null) {
			return false;
		}
		DefaultGraphCell t = findGraphCell(to);
		if (t == null) {
			return false;
		}
		Object[] edges = DefaultGraphModel.getEdgesBetween(jgraph.getModel(), s, t, false);
		if (edges == null || edges.length == 0) {
			return false;
		}
		DefaultEdge edge = (DefaultEdge) edges[0];
		GraphConstants.setLineWidth(edge.getAttributes(), w);
		
		return true;
	}
	/**
	 * Returns the node coordinates.
	 * @param n
	 * @return
	 */
	public Point getNodePosition(String n) {
		DefaultGraphCell cell = findGraphCell(n);
		if (cell == null) {
			return null;
		}
		
		JGraphFacade facade = new JGraphFacade(jgraph.getGraphLayoutCache());
		Rectangle r = facade.getBounds(cell).getBounds();
		
		Point p = new Point((int) r.getX(), (int) r.getY());
		
		return p;
	}
	/**
	 * Sets the coordinates for the given node.
	 * @param n
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean setNodePosition(String n, int x, int y) {
		DefaultGraphCell cell = findGraphCell(n);
		if (cell == null) {
			return false;
		}
		
		//Point2D p = new Point(x, y);
		//GraphConstants.setOffset(cell.getAttributes(), p);
		JGraphFacade facade = new JGraphFacade(jgraph.getGraphLayoutCache());
		facade.setLocation(cell, x, y);
		Map nested = facade.createNestedMap(true, false);
		jgraph.getGraphLayoutCache().edit(nested);
		return true;
	}
	/**
	 * Creates a new graph node.
	 * @param id
	 * @param descriptor
	 */
	private DefaultGraphCell createNode(String descriptor) {
		String id = descriptor;//"" + getVerticesCount(jgraph);
		NodeData nd = new NodeData(id, descriptor);
		DefaultGraphCell cell = new DefaultGraphCell(nd);
		GraphConstants.setGradientColor(cell.getAttributes(), Color.green);
		GraphConstants.setOpaque(cell.getAttributes(), true);
		if (lastVisitedVertex != null) {
			//Set bounds
			Rectangle2D r = GraphConstants.getBounds(lastVisitedVertex.getAttributes());
			GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(
					r.getMaxX()+40, r.getMaxY()+40, 2*JGraphCircleView.RADIUS, 2*JGraphCircleView.RADIUS));

		} else {
			GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(
					40, 40, 2*JGraphCircleView.RADIUS, 2*JGraphCircleView.RADIUS));
		}
		//GraphConstants.setBorder(cell.getAttributes(), BorderFactory
		//		.createRaisedBevelBorder());
		//Set black border
		GraphConstants.setBorderColor(cell.getAttributes(), Color.black);
		cell.addPort();
		
		jgraph.getGraphLayoutCache().insert(cell);
		
		return cell;
	}
	/**
	 * Creates an edge between the given nodes.
	 * @param source
	 * @param target
	 */
	private void createEdge(DefaultGraphCell source, DefaultGraphCell target) {
		DefaultEdge edge = new DefaultEdge();
		edge.setSource(source.getChildAt(0));
		edge.setTarget(target.getChildAt(0));
		
		GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_CLASSIC);
		GraphConstants.setEndFill(edge.getAttributes(), true);
		
		jgraph.getGraphLayoutCache().insert(edge);
	}
	/**
	 * Initializes the graph.
	 *
	 */
	private void initGraph() {
		jgraph = new JGraph(new GraphModel());
		jgraph.getGraphLayoutCache().setFactory(new CellViewFactory());
		jgraph.setCloneable(true);

		// Enable edit without final RETURN keystroke
		jgraph.setInvokesStopCellEditing(true);

		// When over a cell, jump to its default port (we only have one, anyway)
		jgraph.setJumpToDefaultPort(true);
		
		jgraph.setSizeable(false);
	}
	/**
	 * Adds components to the panel.
	 *
	 */
	private void populateContentPane() {
		setLayout(new BorderLayout());
		// Add the Graph as Center Component
		add(new JScrollPane(jgraph), BorderLayout.CENTER);
	}
	/**
	 * Searches for a graph vertex with a given description.
	 * @param desc
	 * @return
	 */
	public DefaultGraphCell findGraphCell(String desc) {
		Object[] list = getVertices(jgraph);
		for (int i = 0; i < list.length; i++) {
			Object v = list[i];
			if (v instanceof DefaultGraphCell) {
				DefaultGraphCell cell = (DefaultGraphCell) v;
				NodeData nd = (NodeData) cell.getUserObject();
				if (equalDescriptors(nd.getDescriptor(), desc)) {//TODO
					return cell;
				}
			}
		}
		
		return null;
	}
	/**
	 * Returns all grph vertices.
	 * @return
	 */
	public Object[] getVertices() {
		return getVertices(jgraph);
	}
	/**
	 * Compare for equality two node descriptors.
	 * @param desc1
	 * @param desc2
	 * @return
	 */
	private boolean equalDescriptors(String desc1, String desc2) {
		return desc1.equals(desc2);
	}

	/**
	 * Returns an array with all graph vertices.
	 * @param jgraph
	 * @return
	 */
	public static Object[] getVertices(JGraph jgraph) {
		List list = DefaultGraphModel.getDescendants(jgraph.getModel(), DefaultGraphModel
				.getRoots(jgraph.getModel()));
		
		Vector v = new Vector();
		for (int i = 0 ; i < list.size(); i++) {
			if (DefaultGraphModel.isVertex(jgraph.getModel(), list.get(i))) {
				v.add(list.get(i));
			}
		}
		
		return v.toArray();
	}
	/**
	 * Returns the number of nodes in the graph.
	 * @param jgraph
	 * @return
	 */
	public static int getVerticesCount(JGraph jgraph) {
		return getVertices(jgraph).length;
	}
}
