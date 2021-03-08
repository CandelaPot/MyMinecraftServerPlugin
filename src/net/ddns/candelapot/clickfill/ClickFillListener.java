package net.ddns.candelapot.clickfill;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class ClickFillListener implements Listener
{
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) 
	{
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			PlayerInventory inv = e.getPlayer().getInventory();
			ItemStack main = inv.getItemInMainHand();
			if(main != null && main.getType() == Material.BLAZE_ROD)
			{
				ItemStack sub = inv.getItemInOffHand();
				if(sub != null && sub.getType() != Material.AIR)
				{
					int amount = 0;
					ItemStack item;
					for(int i = 0; i < 36; i++)
					{
						item = inv.getItem(i);
						if(item != null && sub.getType() == item.getType() && sub.getData().getData() == item.getData().getData())
						{
							amount += item.getAmount();
						}
					}
					amount += sub.getAmount();
					int amount2 = 0;
					if(amount < 27)
					{
						for(int i = 0; i < 27; i++)
						{
							item = e.getPlayer().getEnderChest().getItem(i);
							if(item != null && sub.getType() == item.getType() && sub.getData().getData() == item.getData().getData())
							{
								amount2 += item.getAmount();
							}
						}
					}
					int durability = getRodDurability(main);
					int need = 0;
					Location loc1 = e.getClickedBlock().getLocation();
					Location loc2;
					for(int x = -1; x < 2; x++)
					{
						for(int y = -1; y < 2; y++)
						{
							for(int z = -1; z < 2; z++)
							{
								loc2 = new Location(loc1.getWorld(), loc1.getX() + x, loc1.getY() + y, loc1.getZ() + z);
								if(loc2.getBlock() == null || loc2.getBlock().getType() == Material.AIR)
								{
									need++;
									loc2.getBlock().setType(sub.getType());
									loc2.getBlock().setData(sub.getData().getData());
									if(need >= durability)
									{
										ItemStack reduce = new ItemStack(sub);
										main.setType(Material.AIR);
										if(need >= amount)
										{
											amount--;
											reduce.setAmount(amount);
											inv.removeItem(reduce);
											reduce.setAmount(need - amount);
											e.getPlayer().getEnderChest().removeItem(reduce);
										}
										else
										{
											reduce.setAmount(need);
											inv.removeItem(reduce);
										}
										if(need == amount + amount2)
										{
											inv.setItemInOffHand(null);
										}
										e.setCancelled(true);
										return;
									}
									if(need >= amount + amount2)
									{
										ItemStack reduce = new ItemStack(sub);
										decreaseRodDurability(main, need);
										if(amount2 > 0)
										{
											reduce.setAmount(amount);
											inv.removeItem(reduce);
											reduce.setAmount(amount2);
											e.getPlayer().getEnderChest().removeItem(reduce);
										}
										else
										{
											reduce.setAmount(need);
											inv.removeItem(reduce);
										}
										inv.setItemInOffHand(null);
										e.setCancelled(true);
										return;
									}
								}
							}
						}
					}
					ItemStack reduce = new ItemStack(sub);
					decreaseRodDurability(main, need);
					if(amount2 > 0 && amount <= need)
					{
						amount--;
						reduce.setAmount(amount);
						inv.removeItem(reduce);
						reduce.setAmount(need - amount);
						e.getPlayer().getEnderChest().removeItem(reduce);
					}
					else
					{
						reduce.setAmount(need);
						inv.removeItem(reduce);
					}
					e.getPlayer().updateInventory();
					e.setCancelled(true);
					return;
				}
			}
		}
	}
	public int getRodDurability(ItemStack rod)
	{
		ItemMeta meta = rod.getItemMeta();
		List<String> lore = meta.getLore();
		if(lore == null)
		{
			lore = new ArrayList<>();
		}
		if(lore.isEmpty())
		{
			lore.add(ChatColor.AQUA + "耐久値: 6400/6400");
			meta.setLore(lore);
			rod.setItemMeta(meta);
		}
		for(String loop : lore)
		{
			if(loop.startsWith(ChatColor.AQUA + "耐久値: "))
			{
				return Integer.parseInt(loop.split(" ")[1].split("/")[0]);
			}
		}
		return 0;
	}
	public void decreaseRodDurability(ItemStack rod, int amount)
	{
		ItemMeta meta = rod.getItemMeta();
		List<String> lore = meta.getLore();
		if(lore.isEmpty())
		{
			lore.add(ChatColor.AQUA + "耐久値: 6400/6400");
			meta.setLore(lore);
			rod.setItemMeta(meta);
		}
		for(int i = 0; i< lore.size(); i++)
		{
			String loop = lore.get(i);
			if(loop.startsWith(ChatColor.AQUA + "耐久値: "))
			{
				int durability = (Integer.parseInt(loop.split(" ")[1].split("/")[0]) - amount);
				if(durability <= 0)
				{
					rod.setType(Material.AIR);
				}
				else
				{
					lore.set(i, ChatColor.AQUA + "耐久値: " + durability + "/6400");
					meta.setLore(lore);
					rod.setItemMeta(meta);
				}
				return;
			}
		}
	}
}
