//package tailmaster.gui;

import javax.swing.*;
import javax.swing.plaf.metal.MetalIconFactory;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * User: Halil KARAKOSE
 * Date: Jan 18, 2009
 * Time: 8:43:25 PM
 */
public class CloseButtonTabbedPane extends JTabbedPane {
		UserToolBar userToolBar;
		JPanel panel1;
		
	public CloseButtonTabbedPane() {
	userToolBar=new UserToolBar();
	panel1=userToolBar.getPanel();
	}

//	@Override
	public void addTab(String title, Icon icon, Component component, String tip) {
		super.addTab(title, icon, component, tip);
		int count = this.getTabCount() - 1;
		setTabComponentAt(count, new CloseButtonTab(component, title, icon));
	}

	//@Override
	public void addTab(String title, Icon icon, Component component) {
		addTab(title, icon, component, null);
	}

	//@Override
	public void addTab(String title, Component component) {
		addTab(title, null, component);
	}

	public class CloseButtonTab extends JPanel {
		private Component tab;
		private String title;
		Graph graph;

		public CloseButtonTab(final Component tab,final String title, Icon icon) {
			this.tab = tab;
			this.title=title;
			setOpaque(false);
			FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 3, 3);
			setLayout(flowLayout);
			setVisible(true);

			JLabel jLabel = new JLabel(title);
			jLabel.setIcon(icon);
			add(jLabel);
			JButton button = new JButton(MetalIconFactory.getInternalFrameCloseIcon(16));
			button.setMargin(new Insets(0, 0, 0, 0));
			button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			button.addMouseListener(new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					JTabbedPane tabbedPane = (JTabbedPane) getParent().getParent();
					System.out.println("The title is ::::"+tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
					graph=((Panel)((JScrollPane)tabbedPane.getComponentAt(tabbedPane.indexOfTab(title))).getViewport().getView()).getGraph();
							userToolBar.removeGraph(graph);
							panel1.revalidate();
					tabbedPane.remove(tab);
				}

				public void mousePressed(MouseEvent e) {
				}

				public void mouseReleased(MouseEvent e) {
				}

				public void mouseEntered(MouseEvent e) {
					JButton button = (JButton) e.getSource();
					button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
				}

				public void mouseExited(MouseEvent e) {
					JButton button = (JButton) e.getSource();
					button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
				}
			});
			add(button);
		}
		
	}
	public UserToolBar getUserToolBar()
		{
			return userToolBar;
		}
}



