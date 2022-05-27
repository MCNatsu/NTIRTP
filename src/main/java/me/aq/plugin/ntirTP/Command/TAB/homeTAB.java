package me.aq.plugin.ntirTP.Command.TAB;

import me.aq.plugin.ntirTP.NTIRTP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class homeTAB implements TabCompleter {

    private NTIRTP plugin;

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        plugin = NTIRTP.getPlugin();
        Player p =(Player) sender;

        if(command.getName().equalsIgnoreCase("home")){

            if(plugin.data.HomeCount(p) <=0){
                return null;
            }

            return plugin.data.getlist(p);
        }

        return null;
    }
}
