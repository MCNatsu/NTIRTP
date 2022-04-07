package me.aq.plugin.ntirTP.Command.Home;

import me.aq.plugin.ntirTP.NTIRTP;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sethome implements CommandExecutor {

    private NTIRTP plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        plugin = NTIRTP.getPlugin();

        if(!plugin.getConfig().getBoolean("EnableHome"))return false;

        Player p = (Player) sender;

        if(args.length != 1){
            p.sendMessage(plugin.format + ChatColor.RED + "請輸入紀錄點名稱!");
            return false;
        }

        if(plugin.data.homeExist(p,args[0])){
            p.sendMessage(plugin.format + ChatColor.RED + "你已經有相同名稱的紀錄點了!");
            return false;
        }

        if(plugin.data.HomeCount(p) >= 5){

            p.sendMessage(plugin.format + ChatColor.RED + "你的紀錄點數量已達上限!" + ChatColor.YELLOW + "(目前記錄點上限:5)");

            return false;
        }

        plugin.data.setHome(args[0],p,p.getServer().getMotd(),p.getLocation());
        p.sendMessage(plugin.format + ChatColor.GREEN + "成功設置名為" + ChatColor.LIGHT_PURPLE + args[0] + ChatColor.GREEN + "的紀錄點");

        return true;
    }
}
