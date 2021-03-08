package net.ddns.candelapot.pricefluctuate;

import java.math.BigDecimal;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.Acrobot.ChestShop.Events.TransactionEvent;
import com.Acrobot.ChestShop.Events.TransactionEvent.TransactionType;

public class PriceFluctuateListener implements Listener
{
	@EventHandler
	public void onTransaction(TransactionEvent e) 
	{
		if(e.getOwner().getName().equals("OfficialShop"))
		{
			double par = e.getTransactionType()==TransactionType.BUY ? 1.01 : 1.98;
			double buy = e.getPrice()*par;
			double sell = e.getPrice()*par/2f;
			BigDecimal bd_buy = new BigDecimal(buy).setScale(2, e.getTransactionType()==TransactionType.BUY ? BigDecimal.ROUND_UP : BigDecimal.ROUND_DOWN);
			BigDecimal bd_sell = new BigDecimal(sell).setScale(2, e.getTransactionType()==TransactionType.BUY ? BigDecimal.ROUND_UP : BigDecimal.ROUND_DOWN);
			double buy_after = bd_buy.doubleValue();
			double sell_after = bd_sell.doubleValue();
			if(buy_after <= 1)
			{
				buy_after = 1;
				sell_after = 0.5;
			}
			if(sell_after <= 0.5)
			{
				buy_after = 1;
				sell_after = 0.5;
			}
			e.getSign().setLine(2, "B " + buy_after + ":" + sell_after + " S");
			e.getSign().update();
			
			e.getOwnerInventory().clear();
			e.getOwnerInventory().addItem(e.getStock());
			e.getOwnerInventory().addItem(e.getStock());
		}
	}
}
