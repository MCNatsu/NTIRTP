package me.aq.plugin.ntirTP.Command.Admin;

import me.aq.plugin.ntirTP.NTIRTP;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setSpawn implements CommandExecutor {

    private NTIRTP plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        plugin = NTIRTP.getPlugin();

        Player p = (Player) sender;

        if(p.hasPermission("NTIRAdmin")){
            if(args.length == 0) {
                Location location = p.getLocation();
                String server = p.getServer().getMotd();
                NTIRTP.data.setSpawn(server,location);
                p.getWorld().setSpawnLocation(location);
                p.sendMessage(NTIRTP.format +ChatColor.GREEN + "成功設置重生點");
            }

        }else {
            p.sendMessage(NTIRTP.format + ChatColor.RED + "你沒有此權限");
        }

        return false;
    }
}
