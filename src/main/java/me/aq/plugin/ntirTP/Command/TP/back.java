package me.aq.plugin.ntirTP.Command.TP;

import me.aq.plugin.ntirTP.NTIRTP;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class back implements CommandExecutor {

    private NTIRTP plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        plugin = NTIRTP.getPlugin();
        double delay = plugin.getConfig().getDouble("TP-WaitTime")*20;
        Player p = (Player) sender;
        Location blocation = p.getLocation();

        if(args.length == 0){


            p.sendMessage(NTIRTP.format + ChatColor.GOLD + "正在傳送 " + ChatColor.GRAY + "請稍後" +ChatColor.RED + delay/20 + ChatColor.GRAY + "秒...");
            Location location = NTIRTP.data.getBack(p.getUniqueId().toString());
            new BukkitRunnable(){
                @Override
                public void run() {
                    p.teleport(location);
                    p.sendMessage(NTIRTP.format + ChatColor.GRAY + "正在傳送至上個紀錄點...");
                    NTIRTP.data.setback(p,p.getServer().getMotd(),blocation);
                }
            }.runTaskLater(plugin, (long) delay);
            return true;
        }
        return true;
    }
}
