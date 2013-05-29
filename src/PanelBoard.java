import java.awt.Dimension;

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
	private CoinComponent[] coins = new CoinComponent[NUM_COIN];
	
	PanelBoard()
	{
		setLayout(null);

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
		
		addCoinEventListener(this);
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
		// If source was not extended by CoinComponent, do not work.
		if (!CoinComponent.class.equals(e.getSource().getClass()))
		{
			return ;
		}
		
		// If new coordination is not proper position, back old position.
		if (e.newX < 0 || PANEL_WIDTH < e.newX ||
			e.newY < 0 || PANEL_HEIGHT < e.newY)
		{
			((CoinComponent)e.getSource()).putCoinAt((int)(e.oldX / CoinComponent.COIN_WIDTH),
													 (int)(e.oldY / CoinComponent.COIN_HEIGHT));
		}
		else
		{
			((CoinComponent)e.getSource()).putCoinAt((int)(e.newX / CoinComponent.COIN_WIDTH),
													 (int)(e.newY / CoinComponent.COIN_HEIGHT));
		}
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		// This code let taking space in upper component
		return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
	}
}
