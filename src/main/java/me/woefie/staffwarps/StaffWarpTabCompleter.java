package me.woefie.staffwarps;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StaffWarpTabCompleter implements TabCompleter {
    public FileConfiguration config = StaffWarps.instance.getConfig();

    List<String> tabs = new ArrayList<String>();
    List<String> warps = new ArrayList<String>();
    List<String> Wresults = new ArrayList<String>();
    List<String> Tresults = new ArrayList<String>();
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (tabs.isEmpty()) {
            if (player.hasPermission("woefie.staffwarp.warp")) {
                tabs.add("warp");
            }
            if (player.hasPermission("woefie.staffwarp.remove")) {
                tabs.add("remove");
            }
            if (player.hasPermission("woefie.staffwarp.set")) {
                tabs.add("set");
            }
        }
            if (1 == args.length) {
                for (String b : tabs) {
                    if (b.toLowerCase().startsWith(args[0].toLowerCase()))
                        Wresults.add(b);

                } return Wresults;
            }
            warps = config.getStringList("-warps-");
            if (2 == args.length && !(args[0].equalsIgnoreCase("set"))) {
                for (String a : warps) {
                    if (player.hasPermission("staffwarp.warp." + a)) {
                        if (a.toLowerCase().startsWith(args[1].toLowerCase()))
                            Tresults.add(a);

                }
            } return Tresults;

        }
        return null;
    }
}
