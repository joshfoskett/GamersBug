package net.gamersbug.main.config;

import java.io.File;
import net.gamersbug.main.util.LobbyUtil;
import net.gamersbug.main.util.TeamUtil;
import net.gamersbug.main.util.WorldUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class MapConfig {
    
    private static FileConfiguration lobbyConfig, gameConfig;
    
    public static FileConfiguration getCurrentConfig() {
        
        return (WorldUtil.isCurrentWorldLobby() ? lobbyConfig : gameConfig);
        
    }
    
    public static FileConfiguration getLobbyConfig() {
        
        return lobbyConfig;
        
    }
    
    public static FileConfiguration getGameConfig() {
        
        return gameConfig;
        
    }
    
    public static void setLobbyConfig() {
        
        File gameConfigFile = new File("lobby", "config.yml");
        
        lobbyConfig = YamlConfiguration.loadConfiguration(gameConfigFile);
        
        LobbyUtil.initLobbyUtil();
        
    }
    
    public static void setGameConfig() {
        
        File gameConfigFile = new File("game", "config.yml");
        
        gameConfig = YamlConfiguration.loadConfiguration(gameConfigFile);
        
        TeamUtil.setTeams();
        
    }
    
}
