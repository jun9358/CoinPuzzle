import java.awt.Point;
import java.util.EventListener;


public interface CoinEventListener extends EventListener
{
	void coinPut(CoinEvent event);
}
