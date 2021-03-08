package net.ddns.candelapot.additionalshop;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.ddns.candelapot.Main;
import net.md_5.bungee.api.ChatColor;

public class PayItem 
{
	private ItemStack item;
	private double pay;
	private boolean once;
	private static ArrayList<PayItem> list = new ArrayList<>();
	public PayItem(ItemStack item, double pay, boolean once)
	{
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.YELLOW + "値段: " + Main.db.format(pay));
		meta.setLore(lore);
		item.setItemMeta(meta);
		this.item = item;
		this.pay = pay;
		this.once = once;
		list.add(this);
	}
	public ItemStack getItem() 
	{
		return item;
	}
	public double getPay() 
	{
		return pay;
	}
	public static PayItem getPayItem(String name)
	{
		for(PayItem item : list)
		{
			if(item.getItem().getItemMeta().getDisplayName().equals(name))
			{
				return item;
			}
		}
		return null;
	}
	public boolean isOnce()
	{
		return once;
	}
}
