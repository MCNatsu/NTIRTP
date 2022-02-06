package me.aq.plugin.ntirTP.Command;

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
                plugin.data.setSpawn(server,location);
                p.getWorld().setSpawnLocation(location);
                p.sendMessage(plugin.format +ChatColor.GREEN + "成功設置重生點");
            }

        }else {
            p.sendMessage(plugin.format + ChatColor.RED + "你沒有此權限");
        }

        return false;
    }
}
