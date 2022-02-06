package me.aq.plugin.ntirTP.Command;

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
        Player p = (Player) sender;
        Location blocation = p.getLocation();

        if(args.length == 0){


            p.sendMessage(plugin.format + ChatColor.GRAY + "請稍後" +ChatColor.RED + "3" + ChatColor.GREEN + "秒後傳送...");
            Location location = plugin.data.getBack(p.getUniqueId().toString());
            new BukkitRunnable(){
                @Override
                public void run() {
                    p.teleport(location);
                    p.sendMessage(plugin.format + ChatColor.YELLOW + "正在傳送至上個紀錄點...");
                    plugin.data.setback(p,p.getServer().getMotd(),blocation);
                }
            }.runTaskLater(plugin, 60);
            return true;
        }
        return true;
    }
}
