package net.ddns.candelapot.autorefill;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class AutoRefillListener implements Listener
{
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(BlockPlaceEvent e) 
	{
		Player player = e.getPlayer();
		PlayerInventory inv = player.getInventory();
		ItemStack hand = null;
		if(e.getHand() == EquipmentSlot.HAND)
		{
			hand = inv.getItemInMainHand();
		}
		else if(e.getHand() == EquipmentSlot.OFF_HAND)
		{
			hand = inv.getItemInOffHand();
		}
		if(hand != null && hand.getAmount() == 1)
		{
			ItemStack item;
			for(int i = 0; i < 36; i++)
			{
				if(i == inv.getHeldItemSlot())
				{
					continue;
				}
				item = inv.getItem(i);
				if(item != null && hand.getType() == item.getType() && hand.getData().getData() == item.getData().getData())
				{
					if(e.getHand() == EquipmentSlot.HAND)
					{
						inv.setItemInMainHand(item);
					}
					else if(e.getHand() == EquipmentSlot.OFF_HAND)
					{
						inv.setItemInOffHand(item);
					}
					
					inv.setItem(i, new ItemStack(Material.AIR));
					e.getPlayer().updateInventory();
					return;
				}
			}
			Inventory inv2 = player.getEnderChest();
			for(int i = 0; i < 27; i++)
			{
				item = inv2.getItem(i);
				if(item != null && hand.getType() == item.getType() && hand.getData().getData() == item.getData().getData())
				{
					if(e.getHand() == EquipmentSlot.HAND)
					{
						inv.setItemInMainHand(item);
					}
					else if(e.getHand() == EquipmentSlot.OFF_HAND)
					{
						inv.setItemInOffHand(item);
					}
					
					inv2.setItem(i, new ItemStack(Material.AIR));
					e.getPlayer().updateInventory();
					return;
				}
			}
		}
	}
}
