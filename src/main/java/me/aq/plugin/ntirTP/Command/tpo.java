package me.aq.plugin.ntirTP.Command;

import me.aq.plugin.ntirTP.NTIRTP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tpo implements CommandExecutor {

    private NTIRTP plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        plugin = NTIRTP.getPlugin();

        Player p = (Player) sender;

        if(!p.hasPermission("NTIR.Admin")){
            p.sendMessage(plugin.format + ChatColor.RED + "你沒有執行此指令的權限!");
            return false;
        }

        if(args.length != 1){
            p.sendMessage(plugin.format + ChatColor.RED + "請輸入要傳送的玩家!");
            return false;
        }

        Player target = Bukkit.getPlayerExact(args[0]);

        if(target == null){
            p.sendMessage(plugin.format = ChatColor.RED + "該玩家不存在或不在線上!");
            return false;
        }

        plugin.data.setback(p,p.getServer().getMotd(),p.getLocation());
        p.teleport(target.getLocation());
        p.sendMessage(plugin.format + ChatColor.YELLOW + "你強制傳送到" + ChatColor.AQUA + target.getDisplayName() + ChatColor.YELLOW + "的身邊");

        return true;
    }
}
