import java.util.EventObject;


public class CoinEvent extends EventObject
{
	public int oldX;
	public int oldY;
	public int newX;
	public int newY;
	
	public CoinEvent(Object source)
	{
		super(source);
	}
}
