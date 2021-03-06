package me.aq.plugin.ntirTP.Command.TP;

import me.aq.plugin.ntirTP.NTIRTP;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cancel implements CommandExecutor {

    private NTIRTP plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        plugin = NTIRTP.getPlugin();

        Player requester = (Player) sender;
        Player target = NTIRTP.data.getTarget(requester);

        if(!NTIRTP.data.existTP(requester)){
            requester.sendMessage(NTIRTP.format + ChatColor.RED + "你沒有已發送的傳送請求!");
            return false;
        }

        if(args.length == 0){
            NTIRTP.data.cancelRequest(requester);
            requester.sendMessage(NTIRTP.format + ChatColor.GREEN + "你已成功取消請求");
            target.sendMessage(NTIRTP.format + ChatColor.YELLOW + "玩家" + ChatColor.LIGHT_PURPLE + requester.getDisplayName() + ChatColor.YELLOW + "傳送給你的請求已被取消!");
        }

        return false;
    }
}
