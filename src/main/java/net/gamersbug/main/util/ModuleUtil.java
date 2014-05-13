package net.gamersbug.main.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModuleUtil {
    
    private static final Map<String, Class> gameClasses = new HashMap<>();
    
    private static final Map<String, Object> gameObjects = new HashMap<>();
    
    private static String currentGameName;
    
    private static Class getGameClass() {
        
        return gameClasses.get(currentGameName);
        
    }
    
    private static Object getGameObject() {
        
        return gameObjects.get(currentGameName);
        
    }
    
    public static void loadGame(String gameName) {
        
        currentGameName = gameName;
        
        loadGameInstance(gameName);
        
    }
    
    private static void loadGameInstance(String gameName) {
        
        if(!gameClasses.containsKey(gameName)) {
           
            try {
                
                String gameInstance = "net.gamersbug.main.game." + gameName.toLowerCase() + "." + gameName;
                
                gameClasses.put(gameName, Class.forName(gameInstance));
                gameObjects.put(gameName, getGameClass().newInstance());
                
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
               
                Logger.getLogger(ModuleUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
        
        try {
            
            Method enableGame;
            
            enableGame = getGameClass().getDeclaredMethod("enableGame", new Class[]{});
            enableGame.invoke(getGameObject());
            
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {

            Logger.getLogger(ModuleUtil.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }
    
    public static void unloadGame() {
        
        if(getGameObject() != null) {
        
            unloadGameInstance();
        
        }
        
    }
    
    private static void unloadGameInstance() {
        
        try {
            
            Method disableGame;
            
            disableGame = getGameClass().getDeclaredMethod("disableGame", new Class[]{});   
            disableGame.invoke(getGameObject());
            
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            
            Logger.getLogger(ModuleUtil.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }
    
}
