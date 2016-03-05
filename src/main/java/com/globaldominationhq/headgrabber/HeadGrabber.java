package com.globaldominationhq.headgrabber;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public final class HeadGrabber extends JavaPlugin
{
    HeadSet HeadSetObject = new HeadSet();

    public void onEnable()
    {
        this.saveDefaultConfig();// creates the default config file as outlined in config.yml
        getServer().getPluginManager().registerEvents(new HeadDeath(this), this); // if so, enable the listener
        getLogger().info("HeadGrabber is enabled, enjoy!");
    }

    public void onDisable()
    {
        getLogger().info("HeadGrabber is disabled, have a nice day!");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        // sender is the person who typed the command, cmd is the command used, label is the alias used, and String[] args is an array of the arguments, starting with args[0]
        if (cmd.getName().equalsIgnoreCase("headgrabber"))
        {
            // watch for the command "headgrabber"
            if (!(sender instanceof Player))
            {
                // check to make sure this isn't the console.
                sender.sendMessage("This command cannot be run from the console.");// if it is, let him down gently
            } else
            {
                // otherwise
                Player player = (Player) sender; // set the player variable to the person who executed the command

                if (args.length == 0)
                {
                    // no player argument given
                    if (player.hasPermission("headgrabber.self"))
                    {
                        // permission check
                        // give the player their head
                        PlayerInventory inventory = player.getInventory();// call the players inventory
                        String headName = sender.getName(); // set the name of the player who used the command to the String variable playername
                        ItemStack head;
                        head = HeadSetObject.headSet(headName);
                        inventory.addItem(head);// adds the head to their inventory
                        String filtered = ChatColor.translateAlternateColorCodes('&', HeadGrabber.this.getConfig().getString("headgrabber.can.own"));
                        player.sendMessage(filtered);// Let the player know they're holding they're head

                        return true;
                    } else
                    {
                        String filtered = ChatColor.translateAlternateColorCodes('&', HeadGrabber.this.getConfig().getString("headgrabber.cannot.own"));
                        player.sendMessage(filtered);// tell they player they don't have self-grabbing permission

                        return false;
                    }
                }
                if (args.length == 1)
                {
                    if (player.hasPermission("headgrabber.others"))
                    {
                        // permission check
                        // give the player the specified head
                        String headName = args[0];
                        PlayerInventory inventory = player.getInventory();// call the players inventory
                        ItemStack head;
                        head = HeadSetObject.headSet(headName);
                        inventory.addItem(head);// adds the head to their inventory
                        String filtered = ChatColor.translateAlternateColorCodes('&', HeadGrabber.this.getConfig().getString("headgrabber.can.other"));
                        player.sendMessage(filtered);// message to let the player know they have someone else's head

                        return true;
                    } else
                    {
                        String filtered = ChatColor.translateAlternateColorCodes('&', HeadGrabber.this.getConfig().getString("headgrabber.cannnot.other"));
                        player.sendMessage(filtered);// message to let the player know they're not good enough

                        return false;
                    }
                }

                if (args.length > 1)
                {
                    sender.sendMessage("Invalid arguments, specify only one head at most.");
                    return false;
                }
            }

            return true;// If the plugin grants a head, function will return true.
        }

        return false;// If this hasn't happened the a value of false will be returned.
    }
}