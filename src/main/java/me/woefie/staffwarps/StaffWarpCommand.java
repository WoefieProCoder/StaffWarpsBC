package me.woefie.staffwarps;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StaffWarpCommand implements CommandExecutor {
    List<String> warps = new ArrayList<String>();
    public FileConfiguration config = StaffWarps.instance.getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("staffwarp")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "You cant use staffwarps!");
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("woefie.staffwarp")) {
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You do not have permission to use staffwarps!");
            }
            if (args.length == 0) {
                // /staffwarp
                player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Wrong command. Usage:");
                player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "- /staffwarp set {warpname}");
                player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "- /staffwarp warp {warpname}");
                player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "- /staffwarp remove {warpname}");
                return true;
            }
            // /staffwarp set
            if (args[0].equalsIgnoreCase("set")) {
                if (args.length >= 2) {
                    if (!player.hasPermission("woefie.staffwarp.set")) return true;
                    Location loc = player.getLocation();
                    warps.add(args[1]);
                    config.set(args[1].toLowerCase(), loc);
                    config.set("-warps-", warps);
                    StaffWarps.instance.saveConfig();
                    player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Warp set!");
                    return true;
                } else {
                    player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Wrong command. Usage:");
                    player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "- /staffwarp set {warpname}");
                    return true;
                }
                //staffwarp warp
            } else if (args[0].equalsIgnoreCase("warp")) {
                String allwarppermission = "woefie.staffwarp.warp.*";
                if (args.length >= 2) {
                    String warpname = args[1].toLowerCase();
                    if(config.getLocation(warpname) == null) {
                        player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD +"This is not a warp");
                        return true;
                    }
                    String warppermission = "staffwarp.warp." + args[1];
                    if (!(player.hasPermission(warppermission) || player.hasPermission(allwarppermission)) ) {
                        player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You dont have permission for this");
                    }
                    Location warp = config.getLocation(warpname);
                    player.teleport(warp);
                    player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You have been teleported to: " + warpname);
                    return true;
                } else {
                    player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Wrong command. Usage:");
                    player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "- /staffwarp warp {warpname}");
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (args.length >= 2) {
                    String warpname = args[1].toLowerCase();
                    //if(player.hasPermission("woefie.staffwarp.remove")) return true;
                    if (config.getLocation(warpname) == null) {
                        player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "This is not a warp");
                        return true;
                    }
                    player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You have deleted: " + warpname);
                    config.set(warpname, null);
                    warps = config.getStringList("-warps-");
                    warps.remove(warpname);
                    config.set("-warps-", warps);
                    StaffWarps.instance.saveConfig();
                    return true;
                } else {
                    player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Wrong command. Usage:");
                    player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "- /staffwarp remove {warpname}");
                    return true;
                }
            }
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Usage: /staffwarp");
            return true;
        }
        return false;
    }
}
