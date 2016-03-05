package com.globaldominationhq.headgrabber;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import java.util.Random;


public class HeadDeath implements Listener{
	public HeadGrabber plugin;
	public HeadDeath(HeadGrabber instance){
		plugin = instance;
	}
	HeadSet HeadSetObject = new HeadSet();
	@EventHandler
	public void playerDeath(PlayerDeathEvent event){
		Random randomGenerator = new Random();
		int chance = randomGenerator.nextInt(100);
		if(!(chance>plugin.getConfig().getInt("headgrabber.drops.death"))){
			String headName = event.getEntity().getName();
			ItemStack head;
			head = HeadSetObject.headSet(headName);
			event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), head);
		}	
	}
}
