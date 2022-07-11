package me.aq.plugin.ntirTP.Data.SQL.Utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Random;

public class RandomTeleportUtils {

    private static final HashSet<Material> unSafe = new HashSet<>();
    static {
        unSafe.add(Material.LAVA);
        unSafe.add(Material.POWDER_SNOW);
        unSafe.add(Material.WATER);
        unSafe.add(Material.CACTUS);
        unSafe.add(Material.FIRE);
        unSafe.add(Material.CAMPFIRE);
        unSafe.add(Material.SOUL_CAMPFIRE);
    }

    public static Location generateLocation(Player player){

        Random random = new Random();

        int x = random.nextInt(3000)+2000;
        int y = 0;
        int z = random.nextInt(3000)+2000;

        boolean b = random.nextBoolean();

        Location currentLocation = player.getLocation();

        int curX = (int) currentLocation.getX();
        int curZ = (int) currentLocation.getZ();

        Location randomLocation = new Location(player.getWorld(), curX+x,y,curZ+z);

        if(!b){
            randomLocation = new Location(player.getWorld(), curX-x,y,curZ-z);
        }

        y = randomLocation.getWorld().getHighestBlockYAt(x,z);
        randomLocation.setY(y);

        return randomLocation;
    }

    public static boolean isLocationSafe(Location location){

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        Block block = location.getWorld().getBlockAt(x,y,z);
        Block above = location.getWorld().getBlockAt(x,y+1,z);
        Block below = location.getWorld().getBlockAt(x,y-1,z);

        return !(unSafe.contains(block.getType())|unSafe.contains(above.getType())|unSafe.contains(below.getType())|above.getType().isSolid());

    }
}
