package me.aq.plugin.ntirTP.Event;

import me.aq.plugin.ntirTP.NTIRTP;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Respawn implements Listener {

    private NTIRTP plugin;

    @EventHandler
    public void Respawn(PlayerRespawnEvent e){

        plugin = NTIRTP.getPlugin();
        Player p = e.getPlayer();

        if(p.getBedSpawnLocation() == null | p.getBedSpawnLocation() == plugin.data.getSpawn(p.getServer().getMotd())){

            e.setRespawnLocation(plugin.data.getSpawn(p.getServer().getMotd()));

        }

    }
}
