package net.ddns.candelapot.enchantshop;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class PayEnchant 
{
	private ItemStack item;
	private short xp;
	private static ArrayList<PayEnchant> list = new ArrayList<>();
	public PayEnchant(ItemStack item, short xp)
	{
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.YELLOW + "消費経験値: " + xp + " Level");
		meta.setLore(lore);
		item.setItemMeta(meta);
		this.item = item;
		this.xp = xp;
		list.add(this);
	}
	public ItemStack getItem() 
	{
		return item;
	}
	public short getXp() 
	{
		return xp;
	}
	public static PayEnchant getPayItem(String name)
	{
		for(PayEnchant item : list)
		{
			if(item.getItem().getItemMeta().getDisplayName().equals(name))
			{
				return item;
			}
		}
		return null;
	}
}
