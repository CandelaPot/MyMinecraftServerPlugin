package net.ddns.candelapot.additionalshop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import net.ddns.candelapot.Utility;

public class AdditionalShopOpenListener implements Listener
{
	private static Inventory menu;
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) 
	{
		Block block = e.getClickedBlock();
		if(block == null)
		{
			return;
		}
		if(block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN)
		{
			Sign sign = (Sign)block.getState();
			if(sign.getLine(1).equals("AdditionalShop"))
			{
				e.getPlayer().openInventory(menu);
			}
		}
	}
	public static void initMenu()
	{
		menu = Bukkit.createInventory(null, 27, "Menu");
		menu.setItem(0, new PayItem(Utility.item(ChatColor.WHITE + "インベントリ1-9マス解放", Material.CHEST), 1000.0, true).getItem());
		menu.setItem(1, new PayItem(Utility.item(ChatColor.WHITE + "インベントリ10-18マス解放", Material.CHEST), 2000.0, true).getItem());
		menu.setItem(2, new PayItem(Utility.item(ChatColor.WHITE + "インベントリ19-27マス解放", Material.CHEST), 3000.0, true).getItem());
		menu.setItem(3, new PayItem(Utility.item(ChatColor.WHITE + "朝にする", Material.COMMAND), 100.0, false).getItem());
		menu.setItem(4, new PayItem(Utility.item(ChatColor.WHITE + "夜にする", Material.COMMAND), 100.0, false).getItem());
		menu.setItem(5, new PayItem(Utility.item(ChatColor.WHITE + "晴れにする", Material.COMMAND), 100.0, false).getItem());
		menu.setItem(6, new PayItem(Utility.item(ChatColor.WHITE + "持ち運びクラフトテーブル", Material.WORKBENCH), 2000.0, true).getItem());
		menu.setItem(7, new PayItem(Utility.item(ChatColor.WHITE + "二段ジャンプ", Material.DIAMOND_BOOTS), 3000.0, false).getItem());
	}
}
