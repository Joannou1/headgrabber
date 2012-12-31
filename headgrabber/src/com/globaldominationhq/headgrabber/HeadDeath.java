package com.globaldominationhq.headgrabber;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class HeadDeath implements Listener{
	HeadSet HeadSetObject = new HeadSet();
	@EventHandler
	public void playerDeath(PlayerDeathEvent event){
		String headName = event.getEntity().getName();
		ItemStack head;
		head = HeadSetObject.headSet(headName);
		event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), head);
	}

}
