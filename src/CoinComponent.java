import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import javax.swing.JComponent;


public class CoinComponent extends JComponent implements MouseMotionListener, MouseListener
{
	private int coinNumber;
	private static final int COIN_WIDTH = 30;
	private static final int COIN_HEIGHT = 30;
	private static Point clkPoint;
	
	CoinComponent(int _coinNumber)
	{
		coinNumber = _coinNumber;
		
		// Add mouse listener of coin
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	}
	
	public void setClickedPoint(Point _clkPoint)
	{
		clkPoint = _clkPoint;
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(Color.BLACK);
		g2.fillOval(0, 0, COIN_WIDTH, COIN_HEIGHT);
		
		AttributedString attrString = new AttributedString(""+coinNumber);
		attrString.addAttribute(TextAttribute.SIZE, 20);
		attrString.addAttribute(TextAttribute.FOREGROUND, Color.white);
		
		g2.drawString(attrString.getIterator(), 10, 23);
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		this.setLocation(e.getLocationOnScreen().x - this.getRootPane().getX() - (int)clkPoint.getX(),
						 e.getLocationOnScreen().y - this.getRootPane().getY() - (int)clkPoint.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if (clkPoint == null)
		{
			clkPoint = new Point(e.getX(), e.getY());
		}
		else
		{
			clkPoint.setLocation(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		
	}
}