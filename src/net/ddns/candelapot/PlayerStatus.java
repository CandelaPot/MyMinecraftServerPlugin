package net.ddns.candelapot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitTask;

public class PlayerStatus 
{
	private UUID uuid;
	public BukkitTask back_task;
	public Location back_loc;
	private List<Integer> payinfo;
	private static ArrayList<PlayerStatus> list = new ArrayList<>();
	PlayerStatus(UUID uuid, List<Integer> index)
	{
		this.uuid = uuid;
		payinfo = index;
		list.add(this);
	}
	public void pay(int index)
	{
		payinfo.add(index);
	}
	public boolean has(int index)
	{
		return payinfo.contains(index);
	}
	public static void init()
	{
		FileConfiguration config = Main.instance.getConfig();
		ConfigurationSection section = config.getConfigurationSection("PlayerStatus");
		if(section != null) 
		{
			Iterator<String> i = section.getValues(false).keySet().iterator();
			while(true) 
			{
				if(!i.hasNext()) 
				{
					break;
				}
				String name = (String)i.next();
				new PlayerStatus(UUID.fromString(name), config.getIntegerList("PlayerStatus." + name));
			}
		}
	}
	public static void save()
	{
		FileConfiguration config = Main.instance.getConfig();
		for(PlayerStatus status : list)
		{
			config.set("PlayerStatus." + status.uuid.toString(), status.payinfo);
		}
		Main.instance.saveConfig();
	}
	public static PlayerStatus getPlayerStatus(UUID uuid)
	{
		for(PlayerStatus status : list)
		{
			if(status.uuid.equals(uuid))
			{
				return status;
			}
		}
		return new PlayerStatus(uuid, new ArrayList<>());
	}
}
