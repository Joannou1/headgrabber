package com.globaldominationhq.headgrabber;
import java.io.IOException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
public final class HeadGrabber extends JavaPlugin{
	HeadSet HeadSetObject = new HeadSet();
	public void onEnable(){
		this.saveDefaultConfig();//creates the default config file as outlined in config.yml
		getServer().getPluginManager().registerEvents(new HeadDeath(), this);	//if so, enable the listener
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			//failed to submit the stats :(
		}
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
							String headName = sender.getName(); //set the name of the player who used the command to the String variable playername
							ItemStack head;
							head = HeadSetObject.headSet(headName);
							inventory.addItem(head);//adds the head to their inventory
							player.sendMessage(HeadGrabber.this.getConfig().getString("headgrabber.can.own"));//message to let the player know it worked
							return true;
						}else{
							sender.sendMessage(HeadGrabber.this.getConfig().getString("headgrabber.cannot.own"));//you're not good enough!
							return false;
						}
					}
					if (args.length == 1){
						if (player.hasPermission("headgrabber.others")){//permission check
							//give the player the specified head
							String headName = args[0];
							PlayerInventory inventory = player.getInventory();//call the players inventory
							ItemStack head;
							head = HeadSetObject.headSet(headName);
							inventory.addItem(head);//adds the head to their inventory
							player.sendMessage(HeadGrabber.this.getConfig().getString("headgrabber.can.other").replaceAll("(&([a-f0-9]))", "\u00A7$2"));//message to let the player know it worked
							return true;
						}else{
							sender.sendMessage(HeadGrabber.this.getConfig().getString("headgrabber.cannot.other").replaceAll("(&([a-f0-9]))", "\u00A7$2"));//you're not good enough!
							return false;
						}	
					}
					if (args.length > 1){
						sender.sendMessage("Invalid arguments, specify only one head at most.");
						return false;
					}
				}
			return true;//If the plugin runs successfully, function will return true.
		}          
		return false;// If this hasn't happened the a value of false will be returned. 
	}

}