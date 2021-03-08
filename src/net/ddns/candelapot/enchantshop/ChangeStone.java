package net.ddns.candelapot.enchantshop;

import net.minecraft.server.v1_11_R1.Enchantment;
import net.minecraft.server.v1_11_R1.EnchantmentSlotType;
import net.minecraft.server.v1_11_R1.EnumItemSlot;
import net.minecraft.server.v1_11_R1.ItemPickaxe;
import net.minecraft.server.v1_11_R1.ItemStack;

public class ChangeStone extends Enchantment
{

	protected ChangeStone(Rarity enchantment_rarity, EnumItemSlot[] aenumitemslot) 
	{
		super(enchantment_rarity, EnchantmentSlotType.DIGGER, aenumitemslot);
		this.c("ChangeStone");
	}
	public boolean canEnchant(ItemStack itemstack) 
	{
		return itemstack.getItem() instanceof ItemPickaxe;
	}
	public int a(int arg0)
	{
		return 15;
	}
	public int b(int arg0) 
	{
		return super.a(arg0) + 50;
	}
	public int getMaxLevel() 
	{
		return 1;
	}
	@Override
	public String d(int i) 
	{
		return "ChangeStone";
	}
/*
	public ChangeStone(int id) 
	{
		super(id);
	}
	@Override
	public boolean canEnchantItem(ItemStack arg0) 
	{
		Bukkit.broadcastMessage("" + (arg0.getType() == Material.WOOD_PICKAXE || arg0.getType() == Material.STONE_PICKAXE || arg0.getType() == Material.IRON_PICKAXE || arg0.getType() == Material.DIAMOND_PICKAXE));
		return arg0.getType() == Material.WOOD_PICKAXE || arg0.getType() == Material.STONE_PICKAXE || arg0.getType() == Material.IRON_PICKAXE || arg0.getType() == Material.DIAMOND_PICKAXE;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) 
	{
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() 
	{
		return null;
	}

	@Override
	public int getMaxLevel() 
	{
		return 1;
	}

	@Override
	public String getName() 
	{
		return "CHANGE_STONE";
	}

	@Override
	public int getStartLevel() 
	{
		return 1;
	}

	@Override
	public boolean isCursed() 
	{
		return false;
	}

	@Override
	public boolean isTreasure() 
	{
		return false;
	}
	*/
}
