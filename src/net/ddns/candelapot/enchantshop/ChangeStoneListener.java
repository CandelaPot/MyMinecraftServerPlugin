package net.ddns.candelapot.enchantshop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChangeStoneListener implements Listener
{
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteractEntity(BlockBreakEvent e) 
	{
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		byte data = e.getBlock().getData();
		if(e.getBlock().getType() == Material.STONE && (data == 1 || data == 3 | data == 5) && item.hasItemMeta() && item.getItemMeta().hasEnchant(new EnchantmentWrapper(200)))
		{
			e.getBlock().setData((byte) 0);
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEnchantItem(EnchantItemEvent e) 
	{
		for(Enchantment enchant : e.getEnchantsToAdd().keySet())
		{
			if(enchant.getId() == 200)
			{
				ItemMeta meta = e.getItem().getItemMeta();
				List<String> lore = meta.getLore();
				if(lore == null)
				{
					lore = new ArrayList<>();
				}
				else if(lore.contains(ChatColor.GRAY + "石変換"))
				{
					return;
				}
				lore.add(ChatColor.GRAY + "石変換");
				meta.setLore(lore);
				e.getItem().setItemMeta(meta);
				break;
			}
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent e) 
	{
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.IRON_BLOCK && e.getPlayer().getInventory().getItemInMainHand() != null)
		{
			ItemStack main = e.getPlayer().getInventory().getItemInMainHand();
			if(main.hasItemMeta() && main.getItemMeta().getLore() != null && main.getItemMeta().getLore().contains(ChatColor.GRAY + "石変換") && main.getItemMeta().getEnchants() != null)
			{
				for(Enchantment enchant : main.getItemMeta().getEnchants().keySet())
				{
					if(enchant.getId() == 200)
					{
						return;
					}
				}
				ItemMeta meta = main.getItemMeta();
				List<String> lore = meta.getLore();
				lore = meta.getLore();
				lore.remove(ChatColor.GRAY + "石変換");
				meta.setLore(lore);
				main.setItemMeta(meta);
				e.getPlayer().getInventory().setItemInMainHand(main);
			}
		}
	}
}
