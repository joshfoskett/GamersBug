package net.gamersbug.main.util;

import java.util.HashMap;
import java.util.List;

import net.gamersbug.main.config.MapConfig;

public class GameUtil {
    
    private static String mapName, gameModeName, gameModeType, mapVersion, mapObjective;
    
    private static Integer minPlayers;
    
    private static final HashMap<String, Boolean> mapGameRules = new HashMap<>(), gameRules = new HashMap<>();;
    
    private static List<String> mapRules, mapCreators;
    
    public static void updateGame() {
        
        mapName      = MapConfig.getGameConfig().getString("map.name");
        mapVersion   = MapConfig.getGameConfig().getString("map.version");
        mapObjective = MapConfig.getGameConfig().getString("map.objective");
        mapCreators  = MapConfig.getGameConfig().getStringList("map.creators");
        mapRules     = MapConfig.getGameConfig().getStringList("map.rules");
        gameModeType = MapConfig.getGameConfig().getString("game.type");
        gameModeName = MapConfig.getGameConfig().getString("game.name");
        minPlayers   = MapConfig.getGameConfig().getInt("game.minplayers");
        
    }
    
    public static String getMapName() {
        
        return mapName;
        
    }
    
    public static String getMapVersion() {
        
        return mapVersion;
        
    }
    
    public static String getMapObjective() {
        
        return mapObjective;
        
    }
    
    public static List<String> getMapRules() {
        
        return mapRules;
        
    }public static List<String> getMapCreators() {
        
        return mapCreators;
        
    }
    
    public static String getGameType() {
        
        return gameModeType;
        
    }
    
    public static String getGameName() {
        
        return gameModeName;
        
    }
    
    public static Integer getMinPlayers() {
        
        return minPlayers;
        
    }
    
    public static HashMap<String, Boolean> getGameRules() {
        
        return mapGameRules;
        
    }
    
    public static void setGameRules() {

        if(gameRules.isEmpty()) {

            gameRules.put("weatherlock",  true);
            gameRules.put("mobspawns",    false);
            gameRules.put("playerdamage", true);
            gameRules.put("falldamage",   true);
            gameRules.put("hunger",       true);
            gameRules.put("friendlyfire", false);
            gameRules.put("blockdamage",  true);
            gameRules.put("firespread",   false);
            gameRules.put("dropitems",    true);
            gameRules.put("interact",     true);
            gameRules.put("heal",         true);

        }
        
        mapGameRules.clear();

        for(String key : gameRules.keySet()) {

            Boolean mapGameRuleBoolean;

            if(MapConfig.getCurrentConfig().isBoolean("game.rules." + key)) {

                mapGameRuleBoolean = MapConfig.getCurrentConfig().getBoolean("game.rules." + key);

            }else{

                mapGameRuleBoolean = gameRules.get(key);

            }

            mapGameRules.put(key, mapGameRuleBoolean);
            
        }

    }
    
}
