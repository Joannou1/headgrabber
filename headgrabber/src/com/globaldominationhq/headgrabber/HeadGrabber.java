package com.globaldominationhq.headgrabber;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import net.minecraft.server.NBTTagCompound;



public final class HeadGrabber extends JavaPlugin {
	
	public void onEnable(){
		getLogger().info("HeadGrabber is enabled, enjoy!");
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
							ItemStack head = new ItemStack (Material.SKULL_ITEM, 1);//set the tag "head" to the Skull item
							String playername = sender.getName(); //set the name of the player who used the command to the String variable playername
							head.setTypeId(3);
							NBTTagCompound tag = ((CraftItemStack)head).getHandle().tag;
							tag.setString("skull", playername);
							inventory.addItem(head);//adds the head to their inventory
							player.sendMessage("You reach up and pull on your head...it comes off, and a new one grows in its place!");//message to let the player know it worked
						}else{
							sender.sendMessage("You don't have permission to grab your head!");//you're not good enough!
							return false;
						}
					}
					if (args.length == 1){
						if (player.hasPermission("headgrabber.all")){//permission check
							//give the player the specified head
							String playername = args[0];
							PlayerInventory inventory = player.getInventory();//call the players inventory
							ItemStack head = new ItemStack (Material.SKULL_ITEM, 1);//set the tag "head" to the Skull item
							head.setTypeId(3);
							NBTTagCompound tag = ((CraftItemStack)head).getHandle().tag;
							tag.setString("skull", playername);
							inventory.addItem(head);//adds the head to their inventory
							player.sendMessage("You concentrate, and the head appears in your hand!");//message to let the player know it worked
						}else{
							sender.sendMessage("You don't have permission to grab someone else's head!");//you're not good enough!
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
