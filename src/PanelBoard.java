import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


public class PanelBoard extends JPanel
{
	private static final int PANEL_WIDTH = 300;
	private static final int PANEL_HEIGHT = 300;
	
	@Override
	public Dimension getPreferredSize()
	{
		// This code let taking space in upper component
		return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
	}
}
