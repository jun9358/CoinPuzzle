import java.awt.Dimension;

import javax.swing.JPanel;


public class PanelBoard extends JPanel
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
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		// This code let taking space in upper component
		return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
	}
}
