import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;


public class PanelBoard extends JPanel implements CoinEventListener
{
	private static final int PANEL_WIDTH = 300;
	private static final int PANEL_HEIGHT = 300;
	private static final int BOARD_SIZE = 10;
	private static final int NUM_COIN = 8;

	protected EventListenerList listenerList = new EventListenerList();
	private int[][] initBoard;
	private int board[][] = new int[BOARD_SIZE][BOARD_SIZE];
	private int solution[][];
	private CoinComponent[] coins = new CoinComponent[NUM_COIN];
	private int cntMoving;
	private int maxMoving;
	
	PanelBoard(int[][] _initBoard, int _solution[][], int _maxMoving)
	{
		initBoard = _initBoard;
		solution = _solution;
		maxMoving = _maxMoving;
		
		setLayout(null);

		initBoard();
		
		addCoinEventListener(this);
	}
	
	public void initBoard()
	{
		cntMoving = 0;
		
		this.removeAll();
		for (int i=0 ; i<BOARD_SIZE ; i++)
		{
			for (int j=0 ; j<BOARD_SIZE ; j++)
			{
				if (initBoard[i][j] != -1)
				{
					coins[initBoard[i][j]] = new CoinComponent(initBoard[i][j]);
					coins[initBoard[i][j]].putCoinAt(j, i);
					add(coins[initBoard[i][j]]);
				}
			}
			
			System.arraycopy(initBoard[i], 0, board[i], 0, initBoard[i].length);
		}
		
		repaint();
	}
	
	public boolean isSolution()
	{
		for (int i=0 ; i<board.length - solution.length ; i++)
		{
			for (int j=0 ; j<board[0].length - solution[0].length ; j++)
			{
				if (isMatch(i, j))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isMatch(int startY, int startX)
	{
		for (int i=0 ; i<solution.length ; i++)
		{
			for (int j=0 ; j<solution[i].length ; j++)
			{
				if ((board[startY + i][startX + j]==-1?0:1) != solution[i][j])
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	public void addCoinEventListener(CoinEventListener listener)
	{
		listenerList.add(CoinEventListener.class, listener);
	}
	
	public void removeCoinEventListener(CoinEventListener listener)
	{
		listenerList.remove(CoinEventListener.class, listener);
	}
	
	public void fireCoinEvent(CoinEvent event)
	{
		Object[] listeners = listenerList.getListenerList();
		
		// Decrease because we added two class that CoinEventListner and CoinEvent for listeners
		for (int i=listeners.length-2 ; i>=0 ; i-=2)
		{
			if (listeners[i] == CoinEventListener.class)
			{
				((CoinEventListener)listeners[i+1]).coinPut(event);
			}
		}
	}
	
	@Override
	public void coinPut(CoinEvent e)
	{
		Point coordPoint = new Point();
		
		// If source was not extended by CoinComponent, do not work.
		if (!CoinComponent.class.equals(e.getSource().getClass()))
		{
			return ;
		}
		
		/* If new coordination is not proper position, back old position. */
		// Check new position coin be adjacent just two coins.
		Point dir[] = {new Point(0, -1), new Point(0, 1), new Point(-1, 0), new Point(1, 0)};
		int cntAdjacent;
		
		cntAdjacent = 0;
		coordPoint.x = (int)(e.newX / CoinComponent.COIN_WIDTH);
		coordPoint.y = (int)(e.newY / CoinComponent.COIN_HEIGHT);
		for (int i=0 ; i<dir.length ; i++)
		{
			if ((0 < coordPoint.y + dir[i].y && coordPoint.y + dir[i].y < board.length) && 		// check out of board
				(0 < coordPoint.x + dir[i].x && coordPoint.x + dir[i].x < board[0].length) &&	// check out of board
				board[coordPoint.y + dir[i].y][coordPoint.x + dir[i].x] != -1 &&				// check exist coin 
				board[coordPoint.y + dir[i].y][coordPoint.x + dir[i].x] !=						// check coin's own self
					board[(int)(e.oldY / CoinComponent.COIN_WIDTH)][(int)(e.oldX / CoinComponent.COIN_WIDTH)])
			{
				cntAdjacent++;
			}
		}
		if (cntAdjacent != 2)
		{		
			coordPoint.x = (int)(e.oldX / CoinComponent.COIN_WIDTH);
			coordPoint.y = (int)(e.oldY / CoinComponent.COIN_HEIGHT);
			((CoinComponent)e.getSource()).putCoinAt(coordPoint.x, coordPoint.y);
			
			return ;
		}
		
		// Check new position coin is out of board.
		if (e.newX < 0 || PANEL_WIDTH < e.newX ||
			e.newY < 0 || PANEL_HEIGHT < e.newY)
		{
			coordPoint.x = (int)(e.oldX / CoinComponent.COIN_WIDTH);
			coordPoint.y = (int)(e.oldY / CoinComponent.COIN_HEIGHT);
			((CoinComponent)e.getSource()).putCoinAt(coordPoint.x, coordPoint.y);
		}
		// Check new position coin should be overlay other coin.
		else if (board[(int)(e.newY / CoinComponent.COIN_HEIGHT)][(int)(e.newX / CoinComponent.COIN_WIDTH)] != -1)
		{
			coordPoint.x = (int)(e.oldX / CoinComponent.COIN_WIDTH);
			coordPoint.y = (int)(e.oldY / CoinComponent.COIN_HEIGHT);
			((CoinComponent)e.getSource()).putCoinAt(coordPoint.x, coordPoint.y);
		}
		// If all thing is good, move coin.
		else
		{
			coordPoint.x = (int)(e.newX / CoinComponent.COIN_WIDTH);
			coordPoint.y = (int)(e.newY / CoinComponent.COIN_HEIGHT);
			((CoinComponent)e.getSource()).putCoinAt(coordPoint.x, coordPoint.y);
			
			board[coordPoint.y][coordPoint.x]
				= board[e.oldY / CoinComponent.COIN_HEIGHT][e.oldX / CoinComponent.COIN_WIDTH];
			board[e.oldY / CoinComponent.COIN_HEIGHT][e.oldX / CoinComponent.COIN_WIDTH] = -1;
			
			cntMoving++;
		}
		
		// If moving count done, check solution and show message. 
		if (maxMoving <= cntMoving)
		{
			if (isSolution())
			{
				JOptionPane.showMessageDialog(this,
					"You are success to solve this quiz in " + cntMoving + " moving.",
					"Success",
					JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				JOptionPane.showMessageDialog(this,
					"Your moving count exceed max moving count(" + maxMoving + ").",
					"Failed",
					JOptionPane.WARNING_MESSAGE);
			}
			initBoard();
		}
		
		// If solution is correct before moving count, show message.
		if (cntMoving != 0 && isSolution())
		{
			JOptionPane.showMessageDialog(this,
				"You are genius!!!!\n" + 
				"You solve this quiz before max moving count(" + cntMoving + ").",
				"GENIUS!!!",
				JOptionPane.INFORMATION_MESSAGE);
			initBoard();
		}
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		// This code let taking space in upper component
		return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
	}
}
