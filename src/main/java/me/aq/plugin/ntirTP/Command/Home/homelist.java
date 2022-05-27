package me.aq.plugin.ntirTP.Command.Home;

import me.aq.plugin.ntirTP.NTIRTP;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class homelist implements CommandExecutor {

    private NTIRTP plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        plugin = NTIRTP.getPlugin();

        Player p = (Player) sender;

        if(args.length != 0){
            p.sendMessage(plugin.format + ChatColor.RED + "變數過多!");
            return false;
        }

        if(plugin.data.HomeCount(p) ==0){
            p.sendMessage(plugin.format + ChatColor.RED + "你沒有設置任何紀錄點!" + ChatColor.YELLOW + "請使用/sethome設置");
            return false;
        }

        p.sendMessage(plugin.format + ChatColor.GREEN + "你的紀錄點列表:");
        p.sendMessage(ChatColor.GOLD + plugin.data.getlist(p).toString() + ChatColor.GRAY +
                "(目前紀錄點數量:" + plugin.data.HomeCount(p) + ")");

        return true;
    }
}
