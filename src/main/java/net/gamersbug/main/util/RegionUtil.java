package net.gamersbug.main.util;

import org.bukkit.Location;

public class RegionUtil {
    
    public static boolean inCuboid(Location object, Location minCords, Location maxCords) {
        if(object.getBlockX() < minCords.getBlockX() || object.getBlockX() > maxCords.getBlockX()) {
            return false;
        }else if(object.getBlockY() < minCords.getBlockY() || object.getBlockY() > maxCords.getBlockY()) {
            return false;
        }else if(object.getBlockZ() < minCords.getBlockZ() || object.getBlockZ() > maxCords.getBlockZ()) {
            return false;
        }
        return true;
    }

    public static boolean inCylinder(Location object) {

        /*

        Player player = event.getPlayer();
        Location loc = new Location(player.getWorld(), 0 , 0 , 0);

        double x2 = player.getLocation().getX();
        double x1 = loc.getX();
        double z2 = player.getLocation().getZ();
        double z1 = loc.getZ();
        double radius = 10;

        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(z2 - z1, 2));
        if(distance <= radius){
            player.sendMessage("test");
            player.sendMessage("Your in the zone");
            event.setCancelled(true);
        }else{
            player.sendMessage("Your not it a Zone");
        }

        */

        return true;

    }
    
}