package net.gamersbug.main.util;

import net.gamersbug.main.config.MapConfig;
import net.gamersbug.main.config.ref.Team;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class SpawnUtil {
    
    private static ConfigurationSection spawnSection;
    
    public static Location getSpawnLocation(Player player) {
        
        Location spawnLocation;
        
        Integer getSpawnPosition;
        
        if(WorldUtil.isCurrentWorldLobby()) {
            
            getSpawnPosition = LobbyUtil.getSpawnPosition();
            spawnSection = MapConfig.getLobbyConfig().getConfigurationSection("spawns.spectators");
            LobbyUtil.rotateSpawnPosition();
            
        }else{
            
            Team playersTeam = TeamUtil.getPlayersTeam(player);
            getSpawnPosition = playersTeam.getSpawnPosition();
            spawnSection = MapConfig.getCurrentConfig().getConfigurationSection("spawns." + playersTeam.getKey());
            playersTeam.rotateSpawnPosition();
        
        }
        
        switch(spawnSection.getString("locations." + getSpawnPosition + ".type")) {
            
            case "point": spawnLocation = getPoint(getSpawnPosition); break;
            default: spawnLocation = new Location(WorldUtil.getCurrentWorld(), 0, 0, 0); break;
                
        }
        
        return spawnLocation;
        
    }
    
    private static Location getPoint(Integer spawnPosition) {
        
        String[] xyz = spawnSection.getString("locations." + spawnPosition + ".xyz").split(",");
        Integer yaw = spawnSection.getInt("locations." + spawnPosition + ".yaw");
        
        return new Location(WorldUtil.getCurrentWorld(), Double.valueOf(xyz[0]), Double.valueOf(xyz[1]), Double.valueOf(xyz[2]), Float.valueOf(yaw), 0);
        
    }
    
}
