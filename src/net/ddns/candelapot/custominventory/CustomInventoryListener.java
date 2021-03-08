package net.ddns.candelapot.custominventory;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;

import net.ddns.candelapot.PlayerStatus;

public class CustomInventoryListener implements Listener
{
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) 
	{
		if(!(e.getWhoClicked() instanceof Player))
		{
			return;
		}
		Player player = (Player)e.getWhoClicked();
		if(player.getGameMode() != GameMode.CREATIVE && e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.BARRIER)
		{
			e.setCancelled(true);
			return;
		}
		if(e.getSlotType() == SlotType.ARMOR)
		{
			if(e.getAction() == InventoryAction.PICKUP_HALF)
			{
				if(e.getSlot() == 38)
				{
					ItemStack item = e.getCurrentItem();
					if(item != null)
					{
						player.getInventory().setChestplate(item);
					}
					e.setCancelled(true);
					player.openInventory(player.getEnderChest());
					return;
				}
				else if(e.getSlot() == 39 && PlayerStatus.getPlayerStatus(player.getUniqueId()).has(6))
				{
					ItemStack item = e.getCurrentItem();
					if(item != null)
					{
						player.getInventory().setHelmet(item);
					}
					e.setCancelled(true);
					player.openWorkbench(player.getLocation(), true);
					return;
				}
			}
			else if(e.getAction() == InventoryAction.NOTHING)
			{
				if(e.getSlot() == 38)
				{
					e.setCancelled(true);
					player.openInventory(player.getEnderChest());
					return;
				}
				else if(e.getSlot() == 39 && PlayerStatus.getPlayerStatus(player.getUniqueId()).has(6))
				{
					e.setCancelled(true);
					player.openWorkbench(player.getLocation(), true);
					return;
				}
			}
		}
	}
}
