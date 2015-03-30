import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;

public class PopUpWindow {

    public PopUpWindow(Graph graph,String title,Dimension d) {
    	JFrame frame=new JFrame(title);
    	PopUpPanel panel=new PopUpPanel(graph);
 		JScrollPane scrollPane=new JScrollPane(panel);//panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel.setPreferredSize(d);
		frame.add(scrollPane);
		frame.setSize(400,500);
    	frame.setVisible(true);
    	frame.setResizable(true);
    	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}

class PopUpPanel extends JPanel {
Graph graph;

  PopUpPanel(Graph graph) {
  	this.graph = graph;
  	setBackground(Color.WHITE);
  }

  public void paintComponent(Graphics g) {
        super.paintComponent(g);

		graph.drawAllEdges(g);
		graph.drawAllNodes(g);
    }
}
