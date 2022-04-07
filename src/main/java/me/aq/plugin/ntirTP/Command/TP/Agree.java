package me.aq.plugin.ntirTP.Command.TP;

import me.aq.plugin.ntirTP.NTIRTP;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.Map;

public class Agree implements CommandExecutor {

    private NTIRTP plugin;
    Map<String,Long> StartMoveCheck = new HashMap<String,Long>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        plugin = NTIRTP.getPlugin();

        Player target = (Player) sender;

        if(!plugin.data.existTPT(target)){
            target.sendMessage(plugin.format + ChatColor.RED + "你沒有待確認的請求!");
            return false;
        }

        Player requester = plugin.data.getRequester(target);

        if(args.length == 0){

            if(plugin.data.getType(requester).equalsIgnoreCase("tpaHere")){
                Location location = requester.getLocation();

                target.sendMessage(plugin.format +ChatColor.GREEN + "成功同意請求!");
                requester.sendMessage(plugin.format + ChatColor.GREEN + "對方已同意你的請求");
                target.sendMessage(plugin.format + ChatColor.GRAY + "請稍後" +ChatColor.RED + "3" + ChatColor.GREEN + "秒後傳送...");

                plugin.data.setback(target,target.getServer().getMotd(),target.getLocation());

                new BukkitRunnable(){
                    @Override
                    public void run() {


                        target.teleport(location);
                        target.sendMessage(plugin.format + ChatColor.YELLOW + "正在傳送...");
                        plugin.data.cancelRequest(requester);
                    }
                }.runTaskLater(plugin, 60);
                return true;
            }

            Location location = target.getLocation();

            target.sendMessage(plugin.format +ChatColor.GREEN + "成功同意請求!");
            StartMoveCheck.put(requester.getDisplayName() ,System.currentTimeMillis());
            requester.sendMessage(plugin.format + ChatColor.GREEN + "對方已同意你的請求");
            requester.sendMessage(plugin.format + ChatColor.GRAY + "請稍後" +ChatColor.RED + "3" + ChatColor.GREEN + "秒後傳送...");

            plugin.data.setback(requester,requester.getServer().getMotd(),requester.getLocation());

            new BukkitRunnable(){
                @Override
                public void run() {


                    requester.teleport(location);
                    requester.sendMessage(plugin.format + ChatColor.YELLOW + "正在傳送...");
                    plugin.data.cancelRequest(requester);
                }
            }.runTaskLater(plugin, 60);
            return true;
        }



        return false;
    }
}
