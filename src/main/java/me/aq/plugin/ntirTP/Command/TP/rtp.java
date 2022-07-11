package me.aq.plugin.ntirTP.Command.TP;

import me.aq.plugin.ntirTP.Data.SQL.Utils.RandomTeleportUtils;
import me.aq.plugin.ntirTP.NTIRTP;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class rtp implements CommandExecutor {

    private final NTIRTP plugin = NTIRTP.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            return false;
        }

        Player p = (Player) sender;

        if(plugin.coolDowns.containsKey(p.getUniqueId())){

            Long TimeLeft = plugin.coolDowns.get(p.getUniqueId()) - System.currentTimeMillis();
            if(TimeLeft > 0){
                p.sendMessage(NTIRTP.format + net.md_5.bungee.api.ChatColor.RED + "傳送冷卻尚未結束!"
                        + ChatColor.GRAY + "(剩餘秒數:" + ChatColor.RED + (double) TimeLeft/1000 + ChatColor.GRAY + "秒)");
                return false;
            }

        }

        if(args.length > 0){
            p.sendMessage(NTIRTP.format + "變數過多!");
            return false;
        }

        Location randomLocation = RandomTeleportUtils.generateLocation(p);
        while (!RandomTeleportUtils.isLocationSafe(randomLocation)){
            randomLocation = RandomTeleportUtils.generateLocation(p);
        }

        p.teleport(randomLocation);
        plugin.coolDowns.put(p.getUniqueId(),System.currentTimeMillis() + (long) plugin.getConfig().getDouble("Cooldown")*1000);
        return true;
    }
}
