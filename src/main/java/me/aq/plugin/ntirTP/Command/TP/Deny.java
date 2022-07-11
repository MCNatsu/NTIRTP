package me.aq.plugin.ntirTP.Command.TP;

import me.aq.plugin.ntirTP.NTIRTP;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Deny implements CommandExecutor {

    private NTIRTP plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        plugin = NTIRTP.getPlugin();

        Player target = (Player) sender;

        if(!NTIRTP.data.existTPT(target)){
            target.sendMessage(NTIRTP.format + ChatColor.RED + "你沒有待確認的請求!");
            return false;
        }

        Player requester = NTIRTP.data.getRequester(target);

        if(args.length ==0){

            target.sendMessage(NTIRTP.format + ChatColor.GREEN + "你已拒絕來自" + ChatColor.LIGHT_PURPLE + requester.getDisplayName() + "的傳送請求");
            requester.sendMessage(NTIRTP.format + ChatColor.YELLOW + "對方拒絕了你的請求!");
            NTIRTP.data.cancelRequest(requester);

        }

        return false;
    }
}
