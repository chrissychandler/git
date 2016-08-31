/**
 * 
 */
package edu.georgiasouthern.math.jgraph;

import java.awt.BorderLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;

/**
 * @author Emil Iacob
 *
 */
public class GraphFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String TITLE = "Network Flow";
	
	private static GraphFrame instance;
	
	
	private GraphPanel graphPanel;

	/**
	 * @throws HeadlessException
	 */
	private GraphFrame() throws HeadlessException {
		setTitle(TITLE);
		setSize(1000, 700);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		createContent();
		
	}
	/**
	 * Shows the frame.
	 */
	public static GraphFrame showFrame() {
		
		instance = new GraphFrame();
		
		instance.setVisible(true);
		
		return instance;
	}
	/**
	 * 
	 * @return
	 */
	public static GraphFrame getInstance() {
		return instance;
	}
	/**
	 * 
	 * @return
	 */
	public GraphPanel getGraphPanel() {
		return graphPanel;
	}
	/**
	 * 
	 */
	private void createContent() {
		graphPanel = new GraphPanel();
		getContentPane().add(graphPanel, BorderLayout.CENTER);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		showFrame();

	}

}
