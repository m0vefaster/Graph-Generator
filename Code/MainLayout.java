import java.lang.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.swing.tree.*;
import javax.imageio.*;
import java.awt.image.*;
import java.util.*;
import com.sun.image.codec.jpeg.*;

public class MainLayout extends JFrame
{
    private JMenuBar menubar;
    private JMenu file,edit,view,modes,help,undo1,zoom,saveas,operations,options,nodeShape;
    private JMenuItem nodeColor,edgeColor;
    private JMenuItem newgraph,open,save,print,close,closetab;
    private JMenuItem select_all,unselect_all,remove_sel,remove_all,remove_generated,preserve_generated;
    private JMenuItem undo,redo;
    private JMenuItem txt,gif,jpg;
    private JMenuItem info;
    private JMenuItem fifty,hundred,hundred_fifty,two_hundred;
    private JMenuItem spanning_tree,djikshitra,biconnectivity, bfs, dfs;
    private JCheckBoxMenuItem showlabel,showcoordinates,showproperties,showallgraph,showlog,isDirected,isWeighted;
    private JRadioButtonMenuItem grid,edit1,move,rotate;
    private JRadioButtonMenuItem circle,oval,square,rectangle;
    private ButtonGroup radiogroup,radiogroup2;
    private int count,x,y,value,shape,i;
    private Panel pan;
    private JScrollPane allgraphscrollpane,logscrollpane;
    private Container container;
    private Panel panelname[];
    private UserToolBar userToolBar;
    private UserLog userLog;
    private Graph graph;
	private Properties properties;
	private BiConnectivityTest bct;
	private PopUpWindow popUpWindow;
	private Vector edges, nodes;
	public boolean showLogFlag,showPropertiesFlag;
    public JPanel panel1,panel2;
    public CloseButtonTabbedPane tabpane;
    public Color color;
    public JTree treeoutput;

    public MainLayout()
    {
         super("Graph Generator");
         tabpane=new CloseButtonTabbedPane();

		tabpane.addChangeListener(new ChangeListener()
		{
        	public void stateChanged(ChangeEvent e)
        	{
        		if (showLogFlag)
        		{
	        		graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();
  					if (graph!=null)
	  			    {
  					    userLog=graph.getUserLog();
						if (panel2!=null)
						{
							container.remove(logscrollpane);
							panel2=userLog.getPanel();
							panel2.revalidate();
							panel2.setPreferredSize(new Dimension(195,1000));
	    		        	logscrollpane=new JScrollPane(panel2);
      						container.add(logscrollpane,BorderLayout.WEST);
	      		    	}
		  			}
					container.validate();
					container.repaint();
				}
			}
    	});

        menubar=new JMenuBar();
        container=new Container();
      	userToolBar=tabpane.getUserToolBar();

      	panel1=userToolBar.getPanel();
		file=new JMenu("File");
        file.setMnemonic('F');
        edit=new JMenu("Edit");
        edit.setMnemonic('E');
        options=new JMenu("Options");

        view=new JMenu("Show");
        view.setMnemonic('S');
        modes=new JMenu("Modes");
        modes.setMnemonic('M');
        info=new JMenuItem("Info");
        panelname=new Panel[20];

        newgraph=new JMenuItem("New");
        newgraph.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK ));
        newgraph.addActionListener(new ActionListener()
               {
        	      public void actionPerformed(ActionEvent event)
                  {
                    int count=tabpane.getTabCount();
                    panelname[count]=new Panel(count,userToolBar);//check to see wht happens whn multiple tabs are open
     				panelname[count].setPreferredSize(new Dimension(1000,1000));
                    tabpane.addTab("Untitled"+(count+1),new JScrollPane(panelname[count]));
                    pan=(Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView();
                    properties=panelname[count].getProperties();

					if (showPropertiesFlag)
                    	container.add(properties,BorderLayout.SOUTH);
					container.validate();
                    count++;
                  }
               });

		open=new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK ));
        open.addActionListener(
        	new ActionListener()
            {
            	public void actionPerformed(ActionEvent event)
                {
    	        	int count=tabpane.getTabCount();
                    panelname[count]=new Panel(count,userToolBar);//check to see wht happens whn multiple tabs are open
                    panelname[count].setPreferredSize(new Dimension(1000,1000));
                    tabpane.addTab("Untitled"+count,new JScrollPane(panelname[count]));
                    count++;

                    graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();
                    graph.loadFromFile(getFile());
                }
			});


        save=new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK ));
        save.addActionListener(
        	new ActionListener()
            {
            	public void actionPerformed(ActionEvent event)
                {
                     panelname[count]=new Panel(count,userToolBar);//check to see wht happens whn multiple tabs are open
                     String fileName=JOptionPane.showInputDialog("\n enter the path:  ");
                   	((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph().saveToFile(fileName);
                }
            });

		saveas=new JMenu("Save As");
        txt=new JMenuItem(".txt");
        gif=new JMenuItem(".gif");
        jpg=new JMenuItem(".jpg");
        saveas.add(txt);
        saveas.add(gif);
        saveas.add(jpg);

        txt.addActionListener(
        	new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
      				File newFile=setFile("txt");
					if (newFile!=null)
					{
                   		graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();
                    	if(graph!=null)
      						graph.saveToFile(newFile.getAbsolutePath());
	           			tabpane.setTitleAt(tabpane.getSelectedIndex(),(""+newFile.getName()));
             			container.validate();
               			tabpane.validate();
               			tabpane.repaint();
					}
                }
           });

        gif.addActionListener(
         	new ActionListener()
         	{
         		public void actionPerformed(ActionEvent e)
         		{
         		  	try
         			{
         				File saveFile=setFile("gif");
						if (saveFile!=null)
						{
						    Image img = pan.createImage(pan.getWidth(),pan.getHeight());
    		                Graphics imgG = img.getGraphics();
                     		pan.paint(imgG);
                     		ImageIO.write(toBufferedImage(img),"gif",saveFile);
 			         		JOptionPane.showMessageDialog(null,"Image saved to "+saveFile.toString());
        			 		imgG.dispose();
						}
         			}catch(Exception ex)
         			{
         				System.out.println("Error in saving");

         			}
         		}
         	});


        jpg.addActionListener(
         	new ActionListener()
         	{
         		public void actionPerformed(ActionEvent e)
         		{
         			try
         			{

         				File saveFile=setFile("jpg");
						if (saveFile!=null)
						{
							pan=(Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView();
                 		 	saveAsJPEG(pan,(float).75,saveFile.getAbsolutePath());
						}
         			}
         			catch(Exception ex)
         			{
         				System.out.println("Error in saving");
         			}
         		}
         	});

         close=new JMenuItem("Close");
         close.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_F4, ActionEvent.ALT_MASK ) );
         close.addActionListener(
               new ActionListener()
               {
                   public void actionPerformed(ActionEvent event)
                  {
                     System.exit(0); //remember to add msgbox if not saved
                  }
               }
            );

         print=new JMenuItem("Print");
         print.setAccelerator(KeyStroke.getKeyStroke('P', InputEvent.CTRL_DOWN_MASK ));

         closetab=new JMenuItem("Close Tab");
         closetab.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_F4, ActionEvent.CTRL_MASK ) );
         closetab.addActionListener(
                new ActionListener()
               {
                   public void actionPerformed(ActionEvent event)
                  {
							graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();
							userToolBar.removeGraph(graph);
							panel1.revalidate();

                   			((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).removeAll();
						   tabpane.remove(tabpane.getSelectedIndex());

                  }
               }
            );

         file.add(newgraph);
         file.add(open);
         file.add(close);
         file.add(closetab);
         file.addSeparator();
         file.add(save);
         file.add(saveas);
         file.addSeparator();
         file.add(print);


         showlabel=new JCheckBoxMenuItem("Show Label",false);
         showcoordinates=new JCheckBoxMenuItem("Show Co-ordinates",false);
         showproperties=new JCheckBoxMenuItem("Show Properties",false);
         showallgraph=new JCheckBoxMenuItem("Show All Graph",false);
      	 showlog=new JCheckBoxMenuItem("Show Log Entry",false);

      	 nodeColor=new JMenuItem("Node Color");
         nodeColor.addActionListener
         (
         	new ActionListener()
         	{
         		public void actionPerformed(ActionEvent event)
         		{
         			graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();
         			color=JColorChooser.showDialog(null, "Node Color", graph.getNodeDefaultColor());
         			nodes=(((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph()).getAllNodes();
         			graph.setNodeDefaultColor(color);
		            ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).repaint();
         		}
         	}
         );

         nodeShape=new JMenu("Node Shape");
         circle=new JRadioButtonMenuItem("Circle",true);
         oval=new JRadioButtonMenuItem("Oval",false);
         square=new JRadioButtonMenuItem("Square",false);
         rectangle=new JRadioButtonMenuItem("Rectangle",false);

         nodeShape.add(circle);
         nodeShape.add(oval);
         nodeShape.add(square);
         nodeShape.add(rectangle);

         radiogroup2=new ButtonGroup();
         radiogroup2.add(circle);
         radiogroup2.add(oval);
         radiogroup2.add(square);
         radiogroup2.add(rectangle);

         ShapeHandler shapeHandler=new ShapeHandler();
         circle.addActionListener(shapeHandler);
         oval.addActionListener(shapeHandler);
         square.addActionListener(shapeHandler);
         rectangle.addActionListener(shapeHandler);

         edgeColor=new JMenuItem("Edge Color");
         edgeColor.addActionListener
         (
         	new ActionListener()
         	{
         		public void actionPerformed(ActionEvent event)
         		{
         			graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();
         			color=JColorChooser.showDialog(null, "Edge Color", graph.getEdgeDefaultColor());
         			edges=(((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph()).getAllEdges();
         			graph.setEdgeDefaultColor(color);
		            ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).repaint();
         		}
         	}
         );

         isDirected=new JCheckBoxMenuItem("Directed",false);

         isWeighted=new JCheckBoxMenuItem("Weighted",false);
         isWeighted.addActionListener(
         	new ActionListener()
         	{
         		public void actionPerformed(ActionEvent event)
         		{
	         		if(isWeighted.isSelected())
	         		{
         				graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();
	         			edges=graph.getAllEdges();
	         			for(int i=0;i<edges.size();i++)
	         			{
	         				if(((Edge)edges.elementAt(i)).getWeight()==0)
	         				{
	         					isWeighted.setSelected(false);
	         					JOptionPane.showMessageDialog(null,"All edges of the graph are not weighted","Weighted",JOptionPane.ERROR_MESSAGE);
	         					break;
	         				}
	         			}
	         			((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).setIsWeighted(true);
	         		}
	         		else
	         			((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).setIsWeighted(false);
         		}
         	});

      	 options.add(nodeColor);
         options.add(nodeShape);
         options.add(edgeColor);
         options.addSeparator();
         options.add(isDirected);
         options.add(isWeighted);

         showlabel.addItemListener(
         	new ItemListener() {
            	public void itemStateChanged(ItemEvent e)
                {
                	if (tabpane.getSelectedComponent()!=null)
                 	{
                 	 	graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();
						pan=(Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView();
                 	 	if (showlabel.isSelected()==true)
	                 	{
                 	 		graph.showAllLabels(true);
                 	 		pan=(Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView();
							pan.repaint();
                 	 		container.repaint();
						}

                 	 	else if (showlabel.isSelected()==false)
                 	 	{
                 	 		graph.showAllLabels(false);
	                 	 	pan.repaint();
    	             	 	container.repaint();
                 	 	}
                    }
                 	else
						showlabel.setSelected(false);
               }
               });

         showcoordinates.addItemListener(
                new ItemListener() {
                	public void itemStateChanged(ItemEvent e)
                	{
                	 if (tabpane.getSelectedComponent()!=null)
                 	 {
                 	 	graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();
						pan=(Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView();
                 	 	if (showcoordinates.isSelected()==true)
                 		 {
                 	 		graph.showAllCoordinates(true);
                 	 		pan=(Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView();
							pan.repaint();
                 	 		container.repaint();
							}

                 		 else if (showcoordinates.isSelected()==false)
                 	 	{
                 	 		graph.showAllCoordinates(false);
                 	 		pan.repaint();
                 	 		container.repaint();
                 	 		}
                 	 }
                 	 else
						showcoordinates.setSelected(false);
                 	 }
               });

         showproperties.addItemListener(
                new ItemListener() {
                   public void itemStateChanged(ItemEvent e)
                   {
                 	 if (tabpane.getSelectedComponent()!=null)
                 	 {
                 	 	if (showproperties.isSelected()==true)
                 	 	{
	                 	 	pan=(Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView();
							properties=pan.getProperties();
        	         	 	showPropertiesFlag=true;
            	     	 	container.add(properties,BorderLayout.SOUTH);
                	 	 	container.validate();
                 		 	container.repaint();
						}

                 	 	else if (showproperties.isSelected()==false)
                 	 	{
                 	 		showPropertiesFlag=false;
	                  		container.remove(properties);
    	             	 	container.validate();
        	         	 	container.repaint();
            	     	 }
                 	}
                 	else
						showproperties.setSelected(false);
                 }
              });

         showallgraph.addItemListener(
                new ItemListener() {
                   public void itemStateChanged(ItemEvent e) {
 					if (showallgraph.isSelected()==true)
 					{
	                    panel1.setPreferredSize(new Dimension(100,1000));
	                 	allgraphscrollpane=new JScrollPane(panel1);
      				    container.add(allgraphscrollpane,BorderLayout.EAST);
						container.validate();
						container.repaint();
 					}
 					else if (showallgraph.isSelected()==false)
 				   {
 						container.remove(allgraphscrollpane);
 						container.validate();
 						container.repaint();
 					}
 				}
               });

      	showlog.addItemListener(
                new ItemListener()    {
                   public void itemStateChanged(ItemEvent e) {
                   		if (tabpane.getSelectedComponent()!=null)
		                   {
        		           		if (showlog.isSelected()==true)
                		   		{
                   					showLogFlag=true;
		                   			graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();
  					  				if(graph!=null)
  			  						{
  			   							userLog=graph.getUserLog();
  			   							panel2=userLog.getPanel();
										panel2.revalidate();
										panel2.setPreferredSize(new Dimension(195,1000));
	            		     			logscrollpane=new JScrollPane(panel2);
      				    				container.add(logscrollpane,BorderLayout.WEST);
		  			  				}
								container.validate();
								container.repaint();
			     				}
		                   		else if (showlog.isSelected()==false)
        		           		{
                	   				showLogFlag=false;
                    				container.remove(logscrollpane);
		                    		container.validate();
        		            		container.repaint();
                		   		}
                   		}
                   		else
                   				showlog.setSelected(false);
                  	}
               });

         view.add(showlabel);
         view.add(showcoordinates);
         view.add(showallgraph);
      	 view.add(showproperties);
       	 view.add(showlog);

         edit1=new JRadioButtonMenuItem("Edit",true);
         grid=new JRadioButtonMenuItem("Grid",false);
         move=new JRadioButtonMenuItem("Move",false);
         rotate=new JRadioButtonMenuItem("Rotate",false);

         radiogroup=new ButtonGroup();
         radiogroup.add(edit1);
         radiogroup.add(grid);
         radiogroup.add(move);
         radiogroup.add(rotate);

         modes.add(edit1);
         modes.add(grid);
         modes.add(move);
         modes.add(rotate);

         ModesHandler modeshandler=new ModesHandler();
         edit1.addActionListener(modeshandler);
         grid.addActionListener(modeshandler);
         move.addActionListener(modeshandler);
         rotate.addActionListener(modeshandler);

         edit=new JMenu("Edit");
         edit.setMnemonic('E');
         select_all=new JMenuItem("Select all");
         unselect_all=new JMenuItem("Unselect all");
         remove_sel=new JMenuItem("Remove selected");
         remove_all=new JMenuItem("Remove all");
         remove_generated=new JMenuItem("Remove generated");
         preserve_generated=new JMenuItem("Preserve generated");
         edit.add(select_all);
         edit.add(unselect_all);
         edit.addSeparator();
         edit.add(remove_sel);
         edit.add(remove_all);
         edit.addSeparator();
         edit.add(remove_generated);
         edit.add(preserve_generated);

         EditHandler edithandler=new EditHandler();
         select_all.addActionListener(edithandler);
         unselect_all.addActionListener(edithandler);
         remove_sel.addActionListener(edithandler);
         remove_all.addActionListener(edithandler);
         remove_generated.addActionListener(edithandler);
         preserve_generated.addActionListener(edithandler);

         undo1=new JMenu("Undo");
         undo1.setMnemonic('U');
         undo=new JMenuItem("Undo");
         redo=new JMenuItem("Redo");
         undo1.add(undo);
         undo1.add(redo);

         UndoHandler undohandler=new UndoHandler();
         undo.addActionListener(undohandler);
         redo.addActionListener(undohandler);

         help=new JMenu("Help");
         help.setMnemonic('H');
         info=new JMenuItem("Info");
         help.add(info);

         HelpHandler helphandler=new HelpHandler();
         info.addActionListener(helphandler);

         zoom=new JMenu("Zoom");
         zoom.setMnemonic('Z');
         fifty=new JMenuItem("50%");
         hundred=new JMenuItem("100%");
         hundred_fifty=new JMenuItem("150%");
         two_hundred=new JMenuItem("200%");
         zoom.add(fifty);
         zoom.add(hundred);
         zoom.add(hundred_fifty);
         zoom.add(two_hundred);

         ZoomHandler zoomhandler=new ZoomHandler();
         fifty.addActionListener(zoomhandler);
         hundred.addActionListener(zoomhandler);
         hundred_fifty.addActionListener(zoomhandler);
         two_hundred.addActionListener(zoomhandler);

         operations=new JMenu("Operations");
         operations.setMnemonic('O');
         bfs=new JMenuItem("Breadth first Search");
         dfs=new JMenuItem("Depth First Search");
         spanning_tree=new JMenuItem("Minimum Spanning Tree");
         djikshitra=new JMenuItem("Djikshitra");
         biconnectivity=new JMenuItem("Biconnectivity");
         operations.add(bfs);
         operations.add(dfs);
         operations.add(spanning_tree);
         operations.add(djikshitra);
         operations.add(biconnectivity);


         bfs.addActionListener(new ActionListener()
         {
         	public void actionPerformed(ActionEvent event)
         	{
         		String s=JOptionPane.showInputDialog(null,"Enter the index of the starting node","BFS",JOptionPane.PLAIN_MESSAGE);
         		int index=Integer.parseInt(s);
         		graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();

         		if(contains(index, graph))
         		{
         			BreadthSearch b=new BreadthSearch(graph,index);
         			Graph cloneGraph=(Graph)DeepCopy.copy(graph);
					if (graph.equals(cloneGraph))
						System.out.println("\n\nWrong Results\n\n");
					cloneGraph.setAllNodesEdgesColor(Color.YELLOW,Color.BLUE);

					int count=0;

         			for(int i=0;i<b.bf.size();i++)
         			{
         				count++;
         				int ind=(Integer)b.bf.elementAt(i);
         				Node n=getNode(ind, cloneGraph);
         				n.showCoordinates=false;
         				n.order=count;
         				n.showOrder=true;
         			}
         			Dimension di=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getPreferredSize();
    	            popUpWindow=new PopUpWindow(cloneGraph,"BFS",di);
         		}

         		else
         		{
         			JOptionPane.showMessageDialog(null,"No such node","Error",JOptionPane.ERROR_MESSAGE);
         		}
         	}
         });


         dfs.addActionListener(new ActionListener()
         {
         	public void actionPerformed(ActionEvent event)
         	{
         		String s=JOptionPane.showInputDialog(null,"Enter the index of the starting node","DFS",JOptionPane.PLAIN_MESSAGE);
         		int index=Integer.parseInt(s);
         		graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();

         		if(contains(index, graph))
         		{
         			DepthSearch d=new DepthSearch(graph,index);
         			Graph cloneGraph=(Graph)DeepCopy.copy(graph);
					if (graph.equals(cloneGraph))
						System.out.println("\n\nWrong Results\n\n");
					cloneGraph.setAllNodesEdgesColor(Color.YELLOW,Color.BLUE);
         			int count=0;

         			for(int i=0;i<d.df.size();i++)
         			{
         				count++;
         				int ind=(Integer)d.df.elementAt(i);
         				Node n=getNode(ind, cloneGraph);
         				n.showCoordinates=false;
         				n.order=count;
         				n.showOrder=true;
         			}
         			Dimension di=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getPreferredSize();
    	            popUpWindow=new PopUpWindow(cloneGraph,"DFS",di);
         		}

         		else
         		{
         			JOptionPane.showMessageDialog(null,"No such node","Error",JOptionPane.ERROR_MESSAGE);
         		}
         	}
         });

         spanning_tree.addActionListener(new ActionListener()
         {
         	public void actionPerformed(ActionEvent event)
         	{
         		Connectivity connectivity;
         		SpanningTree spanningTree;

         		pan=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView());
      			connectivity=new Connectivity(pan);

      			if(!isWeighted.isSelected())
      				JOptionPane.showMessageDialog(null,"The graph should be weighted","Error",JOptionPane.ERROR_MESSAGE);

      			else if(!connectivity.getIsConnected())
      				JOptionPane.showMessageDialog(null,"The graph is not connected.\nTree is not possible","Error",JOptionPane.ERROR_MESSAGE);

      			else
      			{
      				pan=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView());
      				spanningTree=new SpanningTree(pan);
      				graph=(Graph)pan.getGraph();

      				Graph cloneGraph=(Graph)DeepCopy.copy(graph);
					if (graph.equals(cloneGraph))
						System.out.println("\n\nWrong Results\n\n");
					cloneGraph.setAllNodesEdgesColor(Color.YELLOW,Color.BLUE);

					for(int i=0;i<spanningTree.edges.size();i++)
					{
						int index=((Edge)spanningTree.edges.elementAt(i)).getIndex();
						System.out.println(index);

						Edge edge=(Edge)cloneGraph.allEdges.elementAt(index-1);
						edge.setColor(Color.RED);
					}

      				Dimension d=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getPreferredSize();
    	            popUpWindow=new PopUpWindow(cloneGraph,"Minimum Spanning Tree",d);
      			}
         	}
         });

		djikshitra.addActionListener(new ActionListener()
         {
         	public void actionPerformed(ActionEvent event)
         	{

         		pan=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView());


      			if(!isWeighted.isSelected())
      				JOptionPane.showMessageDialog(null,"The graph should be weighted","Error",JOptionPane.ERROR_MESSAGE);


      			else
      			{
      				graph=pan.getGraph();
      				Graph cloneGraph=(Graph)DeepCopy.copy(graph);
      				Vector allNodes=cloneGraph.getAllNodes();
      				int nodecount=0;
      				Node node[]=new Node[2];
      				Dijkstra dijkstra;
      				for(int i=0,j=0;i<allNodes.size();i++)
      				{
      					if(((Node)allNodes.get(i)).getSelected())
      					{
      					  nodecount++;
      					  if(nodecount>2)
      					  	break;

      					  node[j]=(Node)allNodes.get(i);
      					  j++;
      					}
      				}

      				if(nodecount!=2)
      					JOptionPane.showMessageDialog(null,"Two nodes must be selected for which shortest distance is to be calculated using Dijkstra's path","Error",JOptionPane.ERROR_MESSAGE);
      				else
      				{

								cloneGraph.setAllNodesEdgesColor(Color.YELLOW,Color.BLUE);

								dijkstra=new Dijkstra(cloneGraph.getAllNodes(),node[0],node[1]);

    	                	  	Dimension d=pan.getPreferredSize();
    	              			popUpWindow=new PopUpWindow(cloneGraph,"BiConnectivity Test",d);


      				}

      			}
         	}
         });

         biconnectivity.addActionListener( new ActionListener() {

                   public void actionPerformed(ActionEvent event)  {
               		   graph = ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();

	                   try{
	            	       if(graph != null)
	            	       	{
								Graph cloneGraph=(Graph)DeepCopy.copy(graph);
								if (graph.equals(cloneGraph))
									System.out.println("\n\nWrong Results\n\n");
								cloneGraph.setAllNodesEdgesColor(Color.YELLOW,Color.BLUE);
								bct = new BiConnectivityTest(cloneGraph.getAllNodes());

    	                	  	Dimension d=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getPreferredSize();
    	              			popUpWindow=new PopUpWindow(cloneGraph,"BiConnectivity Test",d);
	                   		}
	                   	}
	                   catch(Exception e)
	                   {
	                   	  System.out.println(""+e.toString());
	                   }
                   }
               });


         menubar.add(file);
         menubar.add(edit);
         menubar.add(options);
         menubar.add(view);
         menubar.add(modes);
         menubar.add(undo1);
         menubar.add(operations);
         menubar.add(zoom);
         menubar.add(help);

         setJMenuBar(menubar);
		 container.setLayout(new BorderLayout());
         container.add(tabpane,BorderLayout.CENTER);
   		 container.validate();
		 add(container);
      }

   	  private class ModesHandler implements ActionListener
   	  {
   	  	public void actionPerformed(ActionEvent event)
   	  	{
   	  		String s=event.getActionCommand();
   	  		if (s.equals("Grid"))
   	  		{
            	try
            	{
	            	String strrow=JOptionPane.showInputDialog(null,"Enter the number of rows");
            		String strcol=JOptionPane.showInputDialog(null,"Enter the number of columns");
	            	String strdisrow=JOptionPane.showInputDialog(null,"Enter the distance between consecutive rows");
    	        	String strdiscol=JOptionPane.showInputDialog(null,"Enter the distance between consecutive columns");
					int rows=Integer.parseInt(strrow);
					int cols=Integer.parseInt(strcol);
					int disrows=Integer.parseInt(strdisrow);
					int discols=Integer.parseInt(strdiscol);

					graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();
        	 		if (graph!=null)
						graph.setGridValues(rows,cols,disrows,discols);
            	}
            	catch(NumberFormatException e)
            	{
					  System.out.println("-=-=-=-=-=-=-=-Illegal format");
				}
            }
            else if (s.equals("Move"))
            {
              try
              {
              	String x=JOptionPane.showInputDialog(null,"Enter the x-location to be moved");
              	String y=JOptionPane.showInputDialog(null,"Enter the y-location to be moved");

              	graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();
         		if (graph!=null)
					graph.translateAllNodes(Integer.parseInt(x),Integer.parseInt(y));

              }
            	catch(NumberFormatException e)
            	{
					  System.out.println("-=-=-=-=-=-=-=-Illegal format");
				}
            }
            else if (s.equals("Rotate"))
            {
            	try
            	{
            		String s1=JOptionPane.showInputDialog(null,"Enter the angle to be rotated");
            		graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();
         			if (graph!=null)
						graph.rotateAllNodes(Double.parseDouble(s1));
            	}
            	catch(NumberFormatException e)
            	{
					  System.out.println("-=-=-=-=-=-=-=-Illegal format");
				}
            }
   	  	}
   	  }

       private class RadioButtonHandler implements ItemListener
       {
         private int select;
         public RadioButtonHandler(int i)
         {
            select=i;
         }

         public void itemStateChanged(ItemEvent event)
         {
            switch (select)
            {
         	  case 1:
           		break;
           	  case 2:
              {
            	try
            	{
            		String strrow=JOptionPane.showInputDialog(null,"Enter the number of rows");
            		String strcol=JOptionPane.showInputDialog(null,"Enter the number of columns");
            		String strdisrow=JOptionPane.showInputDialog(null,"Enter the distance between consecutive rows");
	            	String strdiscol=JOptionPane.showInputDialog(null,"Enter the distance between consecutive columns");
					int rows=Integer.parseInt(strrow);
					int cols=Integer.parseInt(strcol);
					int disrows=Integer.parseInt(strdisrow);
					int discols=Integer.parseInt(strdiscol);
					graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();
    	     		if (graph!=null)
						graph.setGridValues(rows,cols,disrows,discols);
            	}
            	catch(NumberFormatException e)
            	{
					  System.out.println("-=-=-=-=-=-=-=-Illegal format");
				}
            }
            	break;
            case 3:
            {
              try
              {
	              	String x=JOptionPane.showInputDialog(null,"Enter the x-location to be moved");
    	          	String y=JOptionPane.showInputDialog(null,"Enter the y-location to be moved");
        	      	graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();
         				if (graph!=null)
						graph.translateAllNodes(Integer.parseInt(x),Integer.parseInt(y));
              }
             	catch(NumberFormatException e)
            	{
					  System.out.println("-=-=-=-=-=-=-=-Illegal format");
				}
   		         break;
            }
            case 4:
            {
            	try
            	{

           			System.out.println(""+select);
            		String s=JOptionPane.showInputDialog(null,"Enter the angle to be rotated");
            		graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();
         			if (graph!=null)
						graph.rotateAllNodes(Double.parseDouble(s));
            	}
            	catch(NumberFormatException e)
            	{
					  System.out.println("-=-=-=-=-=-=-=-Illegal format");
				}
            }
    	        break;
          }
       }
    }

    private class EditHandler implements ActionListener
    {
    	public void actionPerformed(ActionEvent event)
        {
        	if(tabpane.getSelectedComponent()!=null)
         	{
           		graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();

         		if (event.getActionCommand().equals("Select all"))
         			graph.selectAll();
            	else if (event.getActionCommand().equals("Unselect all"))
              	{
              		graph.unSelectAll();
               		Layout layout=new Layout(graph);
               		layout.setLayout();
	            }
         	  	else if (event.getActionCommand().equals("Remove selected"))
         	  	{
             		graph.removeSelected();
         	  	}
         	  	else if (event.getActionCommand().equals("Remove all"))
         	  	{
         	  		graph.removeAll();
         	  	}
         	  	else if (event.getActionCommand().equals("Remove generated"))
         	  	{
         	  		graph.removeGenerated();
         	  	}
	         	else
         	  	{}
                ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).repaint();
         }
      }

  	}

	private class UndoHandler implements ActionListener
    {
         public void actionPerformed(ActionEvent event)
         {
             if (event.getActionCommand().equals("Undo"))
             {
              	if(graph==null)
              		System.out.println("Graph nuuuuuullll");
                ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph().undo();
              	((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).repaint();
             }
 	      	 else if (event.getActionCommand().equals("Redo"))
         	 {
             	 ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph().redo();
             	 ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).repaint();
         	 }
         }
    }

    private class HelpHandler implements ActionListener
    {
    	public void actionPerformed(ActionEvent event)
        {
        	if(event.getActionCommand().equals("Info"))
        	{
        		try {
        			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "Graph Generator.pdf");
        		}
        		catch(Exception ex) {
        			  ex.printStackTrace();
        		}
        	}
        }
    }

    private class ShapeHandler implements ActionListener
    {
      	public void actionPerformed(ActionEvent event)
      	{
      		if(event.getActionCommand().equals("Circle"))
      		{
      			shape=1;
      		}
      		if(event.getActionCommand().equals("Square"))
      		{
      			shape=2;
      		}
      		if(event.getActionCommand().equals("Rectangle"))
      		{
      			shape=3;
      		}
      		if(event.getActionCommand().equals("Oval"))
      		{
      			shape=4;
      		}

      		nodes=(((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph()).getAllNodes();
            graph=((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).getGraph();
            graph.setDefaultShape(shape);

            ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).repaint();
      	}
      }

       class ZoomHandler implements ActionListener
      {

          public void actionPerformed(ActionEvent event)
         {
            if(event.getActionCommand().equals("150%"))
            {
               panelname[count].setZoomPercent(1.5);
               ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).setPreferredSize(new Dimension(3600,2700));
               ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).revalidate();
               ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).setMultiple(1.5);

            }

            if(event.getActionCommand().equals("200%"))
            {
               panelname[count].setZoomPercent(2);
               ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).setPreferredSize(new Dimension(4800,3600));
               ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).revalidate();
               ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).setMultiple(2.0);
            }

            if(event.getActionCommand().equals("100%"))
            {
               panelname[count].setZoomPercent(1);
               ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).setPreferredSize(new Dimension(2400,1800));
               ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).revalidate();
               ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).setMultiple(1.0);
            }

            if(event.getActionCommand().equals("50%"))
            {
               panelname[count].setZoomPercent(0.5);
               ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).setPreferredSize(new Dimension(1200,900));
               ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).revalidate();
               ((Panel)((JScrollPane)tabpane.getSelectedComponent()).getViewport().getView()).setMultiple(0.5);
            }

         }

      }

    private BufferedImage toBufferedImage(Image image)
    {
    	if (image instanceof BufferedImage)
    	{
        	return (BufferedImage)image;
    	}
    	image = new ImageIcon(image).getImage();

    // Determine if the image has transparent pixels; for this method's
    // implementation, see e661 Determining If an Image Has Transparent Pixels
    	boolean hasAlpha = hasAlpha(image);

    // Create a buffered image with a format that's compatible with the screen
    	BufferedImage bimage = null;
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	try
    		{
        // Determine the type of transparency of the new buffered image
        	int transparency = Transparency.OPAQUE;
        	if (hasAlpha)
        	{
            transparency = Transparency.BITMASK;
        	}

        // Create the buffered image
        	GraphicsDevice gs = ge.getDefaultScreenDevice();
        	GraphicsConfiguration gc = gs.getDefaultConfiguration();
        	bimage = gc.createCompatibleImage(
                image.getWidth(null), image.getHeight(null), transparency);
  			}
  			 catch (HeadlessException e) {
        // The system does not have a screen
    			}

    	if (bimage == null)
    	{
        // Create a buffered image using the default color model
       		 int type = BufferedImage.TYPE_INT_RGB;
        	if (hasAlpha)
        	 {
            type = BufferedImage.TYPE_INT_ARGB;
        	}
        	bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
    	}

    // Copy image to buffered image
    	Graphics g = bimage.createGraphics();

    // Paint the image onto the buffered image
    	g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
    	g.dispose();

    	return bimage;
	}
// This method returns true if the specified image has transparent pixels
	public static boolean hasAlpha(Image image)
	{
    // If buffered image, the color model is readily available
    if (image instanceof BufferedImage)
    	{
        BufferedImage bimage = (BufferedImage)image;
        return bimage.getColorModel().hasAlpha();
    	}

    // Use a pixel grabber to retrieve the image's color model;
    // grabbing a single pixel is usually sufficient
     PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
    	try {
        	pg.grabPixels();
   			 }
   		 catch (InterruptedException e) {
    		}

    // Get the image's color model
    ColorModel cm = pg.getColorModel();
    return cm.hasAlpha();
	}

	public static void saveAsJPEG(Component component,float quality, String filename)
	{
	    try
	    {
			Dimension d = component.getSize();
			BufferedImage bimage = new BufferedImage(d.width, d.height,BufferedImage.TYPE_INT_RGB);
			component.paint(bimage.createGraphics());
			Graphics2D g = (Graphics2D)bimage.getGraphics();
			g.setPaint(Color.white);

			if(!filename.endsWith(".jpg"))
				filename += ".jpg";
			OutputStream out = new FileOutputStream(filename);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			// Create JPEG encoder
			JPEGEncodeParam jpegParams=encoder.getDefaultJPEGEncodeParam(bimage);
			jpegParams.setQuality(quality, false);

			// Set quality to (quality*100)% for JPEG
			encoder.setJPEGEncodeParam(jpegParams);
			encoder.encode(bimage);

			// Encode image to JPEG and send to browser
			out.close();
			JOptionPane.showMessageDialog(null,"File successfully saved");
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

    public File getFile()
    {
   		JFileChooser fileChooser=new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int result=fileChooser.showOpenDialog(this);

        if(result==JFileChooser.CANCEL_OPTION)
        {
     		System.exit(1);
        }

        File fileName=fileChooser.getSelectedFile();

        if((fileName==null) || (fileName.getName().equals("")))
        {
        	JOptionPane.showMessageDialog(this,"Invalid File Name","Invalid File Name",JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return fileName;
	}

   public File setFile(String ext)
   {

   	     JFileChooser fileChooser=new JFileChooser();
         fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		 int result=fileChooser.showSaveDialog(this);

         if(result==JFileChooser.CANCEL_OPTION)
         {
           	fileChooser.cancelSelection();
         }
         else if(result==JFileChooser.APPROVE_OPTION)
		 {
         	File fileName=fileChooser.getSelectedFile();
         	if((fileName==null) || (fileName.getName().equals("")))
         	{
		   		JOptionPane.showMessageDialog(this,"Invalid File Name","Invalid File Name",JOptionPane.ERROR_MESSAGE);
           		System.exit(0);
            }
			String s=fileName.getPath();
			int size=s.length();

			if(!s.endsWith(ext))
			{
				JOptionPane.showMessageDialog(this,"Illegal format!","ERROR",JOptionPane.ERROR_MESSAGE);
			 	fileChooser.setCurrentDirectory(fileName);
				fileName=setFile(ext);
			}

			if (fileName.exists())
		 	{
		   		int n = JOptionPane.showConfirmDialog(this,"Fileame already exsists .\nDo you want to overwrite the file","Overwrite",JOptionPane.YES_NO_OPTION);
       			if (n!=JOptionPane.YES_OPTION)
		    	{
				  fileChooser.setCurrentDirectory(fileName);
				  fileName=setFile(ext);
				  return fileName;
			 	}
		    }
		   	return fileName;
   		}
		return(null);
	}

	public boolean contains(int index, Graph g)
	{
		for(int i=0;i<g.allNodes.size();i++)
		{
			Node n=(Node)g.allNodes.elementAt(i);
			if(n.getIndex()==index)
				return true;
		}

		return false;
	}

	public Node getNode(int index, Graph gr)
	{
		for(int i=0;i<gr.allNodes.size();i++)
		{
			Node n=(Node)gr.allNodes.elementAt(i);
			if(n.getIndex()==index)
				return n;

		}
		return null;
	}
}



