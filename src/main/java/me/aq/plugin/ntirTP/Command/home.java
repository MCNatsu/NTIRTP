package me.aq.plugin.ntirTP.Command;

import me.aq.plugin.ntirTP.NTIRTP;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class home implements CommandExecutor {

    private NTIRTP plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        plugin = NTIRTP.getPlugin();

        Player p = (Player) sender;

        if(plugin.data.HomeCount(p) <= 0){
            p.sendMessage(plugin.format + ChatColor.RED + "你沒有設置任何紀錄點!" + ChatColor.YELLOW + "請使用/sethome設置");
            return false;
        }

        if(args.length != 1){
            p.sendMessage(plugin.format + ChatColor.RED + "請輸入你要傳送的重生點!");
            return false;
        }

        if(!plugin.data.homeExist(p,args[0])){
            p.sendMessage(plugin.format + ChatColor.RED + "該紀錄點並不存在!" + ChatColor.YELLOW + "使用/sethome設置一個?");
            return false;
        }

        plugin.data.setback(p,p.getServer().getMotd(),p.getLocation());
        Location home = plugin.data.getHome(args[0],p);
        p.sendMessage(plugin.format + ChatColor.GRAY + "請稍後" + ChatColor.RED + "3" + ChatColor.GREEN + "秒後傳送...");
        new BukkitRunnable(){
            @Override
            public void run() {
                p.teleport(home);
                p.sendMessage(plugin.format + net.md_5.bungee.api.ChatColor.YELLOW + "正在傳送至紀錄點...");

            }
        }.runTaskLater(plugin, 60);

        return true;
    }
}
