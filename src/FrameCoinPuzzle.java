import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class FrameCoinPuzzle extends JFrame
{
	private static final int FRAME_INNER_WIDTH = 300;
	private static final int FRAME_INNER_HEIGHT = 400;
	private static final int FRAME_MARGIN = 50;
	
	private JPanel pnlBoard;
	private JLabel lblStatus;
	private JButton btnReset;
	private JButton btnExit;
	private static final int BUTTON_WIDTH = 75;
	private static final int BUTTON_HEIGHT = 25;
	
	FrameCoinPuzzle()
	{
		// This code can be using getInsets() method to get border size
		setVisible(true);
		setVisible(false);
		
		// Set properties
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize
		(
			FRAME_INNER_WIDTH + getInsets().left + getInsets().right,
			FRAME_INNER_HEIGHT + getInsets().top + getInsets().bottom
		);
		setLayout(null);
		
		// Create components
		pnlBoard = new PanelBoard();
		pnlBoard.setBounds
		(
			0, 0,
			pnlBoard.getPreferredSize().width, pnlBoard.getPreferredSize().height
		);
		
		lblStatus = new JLabel("Transform the H to an O");
		lblStatus.setBounds
		(
			0, pnlBoard.getSize().height,
			FRAME_INNER_WIDTH, 50
		);
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnReset = new JButton("Reset");
		btnReset.setBounds
		(
			FRAME_MARGIN, lblStatus.getBounds().y + lblStatus.getBounds().height,
			BUTTON_WIDTH, BUTTON_HEIGHT
		);
		
		btnExit = new JButton("Exit");
		btnExit.setBounds
		(
			FRAME_INNER_WIDTH - (FRAME_MARGIN + BUTTON_WIDTH), lblStatus.getBounds().y + lblStatus.getBounds().height,
			BUTTON_WIDTH, BUTTON_HEIGHT
		);
		
		// Add components
		this.add(pnlBoard);
		this.add(lblStatus);
		this.add(btnReset);
		this.add(btnExit);
	}
}