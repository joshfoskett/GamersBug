package net.gamersbug.main.misc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import net.gamersbug.main.util.RegionUtil;

public class RegionTest {
    
    public static boolean testRegionUtil(Player player, PlayerMoveEvent event) {
        
        Location minCords = new Location(Bukkit.getWorld("world"), 10, 0, 10);
        
        Location maxCords = new Location(Bukkit.getWorld("world"), 20, 200, 20);
        
        if(!RegionUtil.inCuboid(event.getTo(), minCords, maxCords)) {
            
            player.sendMessage("Sorry, but you may not leave this area!");
        
            return true;
            
        }
        
        return false;
        
    }
    
}
