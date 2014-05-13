package net.gamersbug.main.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.gamersbug.main.KitManager;
import net.gamersbug.main.config.MapConfig;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

public class WorldUtil {
    
    public static Integer totalWorldsLoaded = 0, lockWorldTimeId;
    
    private static World lobbyWorld, gameWorld;
    
    private static String currentWorld = "none";
    
    public static void initWorldUtil() {
        
        removeWorldFolder("lobby");
        removeWorldFolder("game");

        createNewWorld("Lobby", PluginUtil.getLobbyMapName());
        
    }
    
    public static void setCurrentWorldLobby() {
        
        currentWorld = "lobby";
        
    }
    
    public static void setCurrentWorldGame() {
        
        currentWorld = "game";
        
    }
    
    public static World getCurrentWorld() {
        
        return (currentWorld.equals("lobby") ? lobbyWorld : gameWorld);
        
    }
    
    public static boolean isCurrentWorldLobby() {
        
        return currentWorld.equals("lobby");
        
    }
    
    public static void createNewWorld(String game, String world) {
        
        Boolean worldTypeIsLobby = game.equalsIgnoreCase("Lobby");
        
        /* Don't load lobby if it's already loaded. */
        
        if(!worldTypeIsLobby || lobbyWorld == null) {
            
            Bukkit.getLogger().log(Level.INFO, "Game: {0} | World: {1}", new Object[]{game, world});
            
            if(!worldTypeIsLobby && gameWorld != null) {
            
                Bukkit.unloadWorld(gameWorld, true);

                removeWorldFolder("game");
                
            }
            
            /* Download world from repo. */
            
            String newWorldName = (game.equalsIgnoreCase("Lobby") ? "lobby" : "game");
            
            if(PluginUtil.getMapMethod().equals("web")) {

                DownloadUtil download = new DownloadUtil();

                String urlPath = PluginUtil.getMapRepo() + game + "/" + world + "/";

                download.downloadDirectory(urlPath, newWorldName + "/");
            
            }else if(PluginUtil.getMapMethod().equals("file")) {
            
                File getMapsFrom = new File(PluginUtil.getMapRepo() + game + "/" + world);
                
                File placeMapsIn = new File(newWorldName);
                
                try {
                    
                    if(!getMapsFrom.exists()) {
                        
                        Bukkit.getLogger().log(Level.INFO, "{0} does not appear to exist.", getMapsFrom.getParent());
                        
                    }
                    
                    FileUtils.copyDirectory(getMapsFrom, placeMapsIn);
                    
                } catch (IOException ex) {
                    
                    Logger.getLogger(WorldUtil.class.getName()).log(Level.SEVERE, null, ex);
                    
                }
                
            }else{
             
                Bukkit.getLogger().log(Level.INFO, "No method to get the maps was specified.");
                
            }

            /* Create instance of world in Bukkit. */

            WorldCreator worldCreator = new WorldCreator(newWorldName);

            worldCreator.environment(World.Environment.NORMAL);
            worldCreator.generateStructures(false);

            worldCreator.createWorld();
            
            if(worldTypeIsLobby) {

                lobbyWorld = Bukkit.getWorld(newWorldName);
                MapConfig.setLobbyConfig();

            }else{

                gameWorld = Bukkit.getWorld(newWorldName);
                MapConfig.setGameConfig();
                GameUtil.updateGame();

            }
        
        }
        
    }
    
    public static void switchToNewWorld() {
        
        if(!isCurrentWorldLobby()) {
            
            setCurrentWorldLobby();
            
        }else{
            
            setCurrentWorldGame();
            
        }
        
        ModuleUtil.unloadGame();
        
        if(isCurrentWorldLobby()) {
            
            ModuleUtil.loadGame("Lobby");
        
        }else{
            
            ModuleUtil.loadGame(PluginUtil.getCurrentGameType());
            
        }
        
        GameUtil.setGameRules();

        KitManager.resetKits();
        
        for(Player player : Bukkit.getOnlinePlayers()) {
            
            if(player.isDead()) {
                
                player.kickPlayer("Please respawn before maps cycle, thank you. :(");
                
            }
            
            PlayerUtil.teleportPlayer(player);
            
        }
        
        if(isCurrentWorldLobby()) {
            
            PlayerUtil.disablePlayerVisibility();
            
            PluginUtil.rotateMapPosition();
            
            createNewWorld(PluginUtil.getCurrentGameType(), PluginUtil.getCurrentGameMap());

            ServerUtil.updatePlayingState(0);

            ServerUtil.updateMapAndGame(GameUtil.getMapName(), GameUtil.getGameName());
            
        }else{
            
            PlayerUtil.enablePlayerVisibility();

            ServerUtil.updatePlayingState(1);
            
        }
        
    }
    
    public static void removeWorldFolder(String folder) {
        
        File gameWorldFolder = new File(folder);
        
        if(gameWorldFolder.exists()) {
            
            try {
                
                FileUtils.deleteDirectory(gameWorldFolder);
                
            } catch (IOException ex) {
                
                Logger.getLogger(WorldUtil.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            
        }
        
    }
    
}