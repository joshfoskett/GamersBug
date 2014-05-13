package net.gamersbug.main.util;

import net.gamersbug.main.config.PluginConfig;

public class PluginUtil {
    
    private static Integer serverNumber, maxPlayers, mapRotationTotal, mapRotationPosition, lobbyCountdown;
    
    private static String serverCategory, hikariConfig, lobbyMapName, mapRepo, mapMethod;
    
    public static void initPlugin() {
        
        PluginConfig.initPluginConfig();

        serverCategory = PluginConfig.getConfig().getString("server.category");
        serverNumber   = PluginConfig.getConfig().getInt("server.number");
        maxPlayers     = PluginConfig.getConfig().getInt("server.max_players");
        hikariConfig   = PluginConfig.getConfig().getString("hikari.config");
        lobbyMapName   = PluginConfig.getConfig().getString("lobby.map");
        lobbyCountdown = PluginConfig.getConfig().getInt("lobby.countdown");
        mapRepo        = PluginConfig.getConfig().getString("map.repo");
        mapMethod      = PluginConfig.getConfig().getString("map.method");
        
        mapRotationTotal = PluginConfig.getConfig().getConfigurationSection("rotation").getKeys(false).size();
        mapRotationPosition = 0;
        
    }

    public static String getServerCategory() {

        return serverCategory;

    }

    public static Integer getServerNumber() {

        return serverNumber;

    }

    public static String getHikariConfig() {

        return hikariConfig;

    }
    
    public static String getLobbyMapName() {
        
        return lobbyMapName;
        
    }

    public static Integer getLobbyCountdown() {

        return lobbyCountdown;

    }
    
    public static Integer getMaxPlayers() {
        
        return maxPlayers;
        
    }
    
    public static String getMapRepo() {
        
        return mapRepo;
        
    }
    
    public static String getMapMethod() {
        
        return mapMethod;
        
    }
    
    public static String getCurrentGameType() {
        
        return PluginConfig.getConfig().getString("rotation." + getMapRotationPosition() + ".game");
        
    }
    
    public static String getCurrentGameMap() {
        
        return PluginConfig.getConfig().getString("rotation." + getMapRotationPosition() + ".map");
        
    }
    
    public static Integer getMapRotationTotal() {
        
        return mapRotationTotal;
        
    }
    
    public static Integer getMapRotationPosition() {
        
        return mapRotationPosition;
        
    }
    
    public static void rotateMapPosition() {
        
        mapRotationPosition = (mapRotationPosition >= mapRotationTotal ? 1 : mapRotationPosition + 1);
        
    }
    
}
