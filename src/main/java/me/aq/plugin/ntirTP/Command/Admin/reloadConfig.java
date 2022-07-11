package me.aq.plugin.ntirTP.Command.Admin;

import me.aq.plugin.ntirTP.NTIRTP;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class reloadConfig implements CommandExecutor {

    private static final NTIRTP plugin = NTIRTP.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            return false;
        }

        Player p = (Player) sender;

        if(!p.isOp()){
            p.sendMessage(NTIRTP.format + ChatColor.RED + "你沒有權限執行此操作");
            return false;
        }

        NTIRTP.format = plugin.config.getString("prefix");
        NTIRTP.tpCoolDown = plugin.config.getDouble("Cooldown");
        NTIRTP.tpWaitTime = plugin.config.getDouble("TP-WaitTime");
        NTIRTP.spawnWaitTime = plugin.config.getDouble("Spawn-WaitTime");
        NTIRTP.HomeWaitTime = plugin.config.getDouble("Home-WaitTime");
        return true;
    }
}
