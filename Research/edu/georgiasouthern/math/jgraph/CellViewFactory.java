/**
 * 
 */
package edu.georgiasouthern.math.jgraph;

import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.VertexView;

/**
 * @author Emil Iacob
 *
 */
public class CellViewFactory extends DefaultCellViewFactory {
	protected VertexView createVertexView(Object v) {
		VertexView view = new JGraphCircleView(v);
		
		return view;
	}
}
