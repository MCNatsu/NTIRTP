package me.aq.plugin.ntirTP.Command.TP;

import me.aq.plugin.ntirTP.NTIRTP;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class spawn implements CommandExecutor {

    private NTIRTP plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        plugin = NTIRTP.getPlugin();

        double delay = plugin.getConfig().getDouble("Spawn-WaitTime")*20;

        Player p = (Player) sender;

        if(args.length != 0){
            return false;
        }

        Location location = NTIRTP.data.getSpawn(p.getServer().getMotd());

        NTIRTP.data.setback(p,p.getServer().getMotd(),p.getLocation());
        p.sendMessage(NTIRTP.format + ChatColor.GOLD + "正在發動轉移魔法 " + ChatColor.GRAY + "請稍後" +ChatColor.RED + delay/20 + ChatColor.GRAY + "秒...");

        new BukkitRunnable(){
            @Override
            public void run() {
                p.teleport(location);
                p.sendMessage(NTIRTP.format + ChatColor.GRAY + "正在傳送至重生點...");
            }
        }.runTaskLater(plugin, (long) delay);

        return true;
    }
}
