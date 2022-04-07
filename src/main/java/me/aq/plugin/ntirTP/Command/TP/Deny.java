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

        Player requester = plugin.data.getRequester(target);

        if(args.length ==0){

            target.sendMessage(plugin.format + ChatColor.GREEN + "你已拒絕來自" + ChatColor.LIGHT_PURPLE + requester.getDisplayName() + "的傳送請求");
            requester.sendMessage(plugin.format + ChatColor.YELLOW + "對方拒絕了你的請求!");
            plugin.data.cancelRequest(requester);

        }

        return false;
    }
}
