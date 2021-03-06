package me.aq.plugin.ntirTP.Command.Home;

import me.aq.plugin.ntirTP.NTIRTP;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class delhome implements CommandExecutor {

    private NTIRTP plugin;
    private static final String format = NTIRTP.format;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        plugin = NTIRTP.getPlugin();

        Player p = (Player) sender;

        if(NTIRTP.data.HomeCount(p) <= 0){
            p.sendMessage(format + ChatColor.RED + "你沒有任何紀錄點可以刪除!" + ChatColor.YELLOW + "請使用/sethome設置");
            return false;
        }

        if(args.length != 1){
            p.sendMessage(format + ChatColor.RED + "請輸入你要刪除的重生點!");
            return false;
        }

        if(!NTIRTP.data.homeExist(p,args[0])){
            p.sendMessage(format + ChatColor.RED + "該紀錄點並不存在!" + ChatColor.YELLOW + "使用/sethome設置一個?");
            return false;
        }

        NTIRTP.data.delHome(args[0] , p);
        p.sendMessage(format + ChatColor.GREEN + "你已成功刪除該紀錄點");

        return true;
    }
}
