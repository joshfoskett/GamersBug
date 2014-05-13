package net.gamersbug.main.util;

import net.gamersbug.main.config.MapConfig;

public class LobbyUtil {
    
    private static Integer spawnTotal, spawnPosition;
    
    public static void initLobbyUtil() {
        
        spawnTotal = MapConfig.getLobbyConfig().getConfigurationSection("spawns.spectators.locations").getKeys(false).size();
        spawnPosition = 1;
        
    }
    
    public static Integer getSpawnTotal() {
        
        return spawnTotal;
        
    }
    
    public static Integer getSpawnPosition() {
        
        return spawnPosition;
        
    }
    
    public static void rotateSpawnPosition() {
        
        spawnPosition = (spawnPosition >= spawnTotal ? 1 : spawnPosition + 1);
        
    }
    
}
