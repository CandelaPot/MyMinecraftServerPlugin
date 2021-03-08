package net.ddns.candelapot;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utility
{
	public static ItemStack item(String name, Material material)
	{
		ItemStack result = new ItemStack(material);
		ItemMeta meta = result.getItemMeta();
		meta.setDisplayName(name);
		result.setItemMeta(meta);
		return result;
	}
}
