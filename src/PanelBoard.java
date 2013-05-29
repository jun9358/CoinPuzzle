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
	private static final int[][] INIT_BOARD = new int[][]
	{
		{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		{-1, -1, -1, 00, -1, -1, 05, -1, -1, -1},
		{-1, -1, -1, 01, 03, 04, 06, -1, -1, -1},
		{-1, -1, -1, 02, -1, -1, 07, -1, -1, -1},
		{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
	};

	protected EventListenerList listenerList = new EventListenerList();
	private int board[][] = new int[BOARD_SIZE][BOARD_SIZE];
	private int solution[][] = new int[][]
	{
		{1, 1, 1},
		{1, 0, 1},
		{1, 1, 1}
	};
	private CoinComponent[] coins = new CoinComponent[NUM_COIN];
	private int cntMoving;
	private int maxMoving;
	
	PanelBoard()
	{
		setLayout(null);
		maxMoving = 4;

		cntMoving = 0;
		for (int i=0 ; i<BOARD_SIZE ; i++)
		{
			for (int j=0 ; j<BOARD_SIZE ; j++)
			{
				if (INIT_BOARD[i][j] != -1)
				{
					coins[INIT_BOARD[i][j]] = new CoinComponent(INIT_BOARD[i][j]);
					coins[INIT_BOARD[i][j]].putCoinAt(j, i);
					add(coins[INIT_BOARD[i][j]]);
				}
			}
		}
		board = INIT_BOARD.clone();
		
		addCoinEventListener(this);
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
		
		// If new coordination is not proper position, back old position.
		if (e.newX < 0 || PANEL_WIDTH < e.newX ||
			e.newY < 0 || PANEL_HEIGHT < e.newY)
		{
			coordPoint.x = (int)(e.oldX / CoinComponent.COIN_WIDTH);
			coordPoint.y = (int)(e.oldY / CoinComponent.COIN_HEIGHT);
			((CoinComponent)e.getSource()).putCoinAt(coordPoint.x, coordPoint.y);
		}
		else if (board[(int)(e.newY / CoinComponent.COIN_HEIGHT)][(int)(e.newX / CoinComponent.COIN_WIDTH)] != -1)
		{
			coordPoint.x = (int)(e.oldX / CoinComponent.COIN_WIDTH);
			coordPoint.y = (int)(e.oldY / CoinComponent.COIN_HEIGHT);
			((CoinComponent)e.getSource()).putCoinAt(coordPoint.x, coordPoint.y);
		}
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
		}
		
		if (isSolution())
		{
			JOptionPane.showMessageDialog(this,
				"You are genius!!!!\n" + 
				"You solve this quiz before max moving count(" + cntMoving + ").",
				"GENIUS!!!",
				JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		// This code let taking space in upper component
		return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
	}
}
