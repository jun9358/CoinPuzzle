import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import javax.swing.JComponent;


public class CoinComponent extends JComponent
{
	private int coinNumber;
	private static final int COIN_WIDTH = 30;
	private static final int COIN_HEIGHT = 30;
	
	CoinComponent(int _coinNumber)
	{
		coinNumber = _coinNumber;
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
}