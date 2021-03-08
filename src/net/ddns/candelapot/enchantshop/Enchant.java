package net.ddns.candelapot.enchantshop;

import net.minecraft.server.v1_11_R1.Enchantment;

public class Enchant 
{
	private Enchantment enchant;
	private int level;
	public Enchant(Enchantment enchant, int level)
	{
		this.enchant = enchant;
		this.level = level;
	}
	public Enchantment getEnchant() {
		return enchant;
	}
	public int getLevel() {
		return level;
	}
}
