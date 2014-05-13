package net.gamersbug.main.game.survivalgames;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;

public class ChestManager {
    
    private static final List<Location> loadedChests = new ArrayList<>();
    
    public static void setAccessedLocation(Location chestLocation) {
        
        loadedChests.add(chestLocation);
        
    }
    
    public static boolean hasChestBeenAccessed(Location chestLocation) {
        
        if(!loadedChests.contains(chestLocation)) {
            
            setAccessedLocation(chestLocation);
            
            return false;
            
        }
        
        return true;
        
    }
    
    public static void resetChests() {
        
        loadedChests.clear();
        
    }
    
}
