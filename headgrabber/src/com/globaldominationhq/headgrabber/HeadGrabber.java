package com.globaldominationhq.headgrabber;

import java.io.IOException;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.PlayerInventory;
import net.minecraft.server.NBTTagCompound;


public final class HeadGrabber extends JavaPlugin {
	
	public void onEnable(){
		getLogger().info("HeadGrabber is enabled, enjoy!");
		this.saveDefaultConfig();//creates the default config file as outlined in config.yml
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			//failed to submit the stats :(
		}
	}
	
	public void onDisable(){
		getLogger().info("HeadGrabber is disabled, have a nice day!");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
	//sender is the person who typed the command, cmd is the command used, label is the alias used, and String[] args is an array of the arguments, starting with args[0]
		if(cmd.getName().equalsIgnoreCase("headgrabber")){ //watch for the command "headgrabber" 
			if (!(sender instanceof Player)) {//check to make sure this isn't the console.
				sender.sendMessage("This command cannot be run from the console.");//if it is, let him down gently
				} else {//otherwise
					Player player = (Player) sender; //set the player variable to the person who executed the command
					if (args.length == 0){//no player argument given
						if (player.hasPermission("headgrabber.self")){//permission check
							//give the player their head
							PlayerInventory inventory = player.getInventory();//call the players inventory
							String playername = sender.getName(); //set the name of the player who used the command to the String variable playername
							CraftItemStack head;
								head = new CraftItemStack(Material.SKULL_ITEM,1,(short)3);//set the ItemStack "head" to the Skull item
							NBTTagCompound headNBT = new NBTTagCompound();
							headNBT.setString("SkullOwner", playername);
							head.getHandle().tag = headNBT;
							inventory.addItem(head);//adds the head to their inventory
							player.sendMessage(HeadGrabber.this.getConfig().getString("headgrabber.can.own"));//message to let the player know it worked
						}else{
							sender.sendMessage(HeadGrabber.this.getConfig().getString("headgrabber.cannot.own"));//you're not good enough!
							return false;
						}
					}
					if (args.length == 1){
						if (player.hasPermission("headgrabber.all")){//permission check
							//give the player the specified head
							String playername = args[0];
							PlayerInventory inventory = player.getInventory();//call the players inventory
							CraftItemStack head;
								head = new CraftItemStack(Material.SKULL_ITEM,1,(short)3);//set the ItemStack "head" to the Skull item
							NBTTagCompound headNBT = new NBTTagCompound();
							headNBT.setString("SkullOwner", playername);
							head.getHandle().tag = headNBT;
							inventory.addItem(head);//adds the head to their inventory
							player.sendMessage(HeadGrabber.this.getConfig().getString("headgrabber.can.other"));//message to let the player know it worked
						}else{
							sender.sendMessage(HeadGrabber.this.getConfig().getString("headgrabber.cannot.other"));//you're not good enough!
							return false;
						}	
					}
					if (args.length > 1){
						sender.sendMessage("Invalid arguments, specify one head at most.");
						return false;
					}
				}
			return true;//If the plugin runs successfully, function will return true.
		}  
	        
		return false;// If this hasn't happened the a value of false will be returned. 
	}

}
