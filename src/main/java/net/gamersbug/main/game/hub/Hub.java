package net.gamersbug.main.game.hub;

import net.gamersbug.main.GamersBug;
import net.gamersbug.main.MessageManager;
import net.gamersbug.main.config.MapConfig;
import net.gamersbug.main.game.hub.event.HubEvent;
import net.gamersbug.main.game.hub.object.ServerCategoryObject;
import net.gamersbug.main.util.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Hub {

    private static List<ServerCategoryObject> serverCategoryEntitiesObjectList = new ArrayList<>();

    private final HubEvent hubEvent = new HubEvent();

    private static Integer serverCategoryEntitiesTaskID;

    public void enableGame() {

        TeamUtil.setTeamsLocked();

        Bukkit.getPluginManager().registerEvents(hubEvent, GamersBug.getPlugin());

        initServerCategories();

        Bukkit.getLogger().log(Level.INFO, "[GamersBug] Hub has been enabled.");

    }

    public void disableGame() {

        serverCategoryEntitiesObjectList.clear();

        Bukkit.getServer().getScheduler().cancelTask(serverCategoryEntitiesTaskID);

        HandlerList.unregisterAll(hubEvent);

        Bukkit.getLogger().log(Level.INFO, "[GamersBug] Hub has been disabled.");

    }

    public void initServerCategories() {

        ConfigurationSection serverCategoryConfig = MapConfig.getCurrentConfig().getConfigurationSection("game.server_categories");

        for(String entityKey : serverCategoryConfig.getKeys(false)) {

            String entityName = MessageManager.addChatColorToString(serverCategoryConfig.getString(entityKey + ".name"));

            String serverCategory = serverCategoryConfig.getString(entityKey + ".category");

            ServerCategoryObject serverCategoryObject = new ServerCategoryObject(entityName, serverCategory);

            if(serverCategoryConfig.isSet(entityKey + ".entity")) {

                String locPoints[] = serverCategoryConfig.getString(entityKey + ".entity.location").split(",");

                Location entityLocation = new Location(WorldUtil.getCurrentWorld(), Double.valueOf(locPoints[0]), Double.valueOf(locPoints[1]), Double.valueOf(locPoints[2]), Float.valueOf(locPoints[3]), 0);

                Villager serverVillager = (Villager) WorldUtil.getCurrentWorld().spawnEntity(entityLocation, EntityType.VILLAGER);

                serverVillager.setCustomName(entityName);

                serverVillager.setCustomNameVisible(true);

                serverCategoryObject.setEntity(serverVillager, entityLocation);

            }

            serverCategoryEntitiesObjectList.add(serverCategoryObject);

        }

        serverCategoryEntitiesTimer();

    }

    public static List<ServerCategoryObject> getServerCategories() {

        return serverCategoryEntitiesObjectList;

    }

    public Runnable serverCategoryEntitiesTimer() {

        serverCategoryEntitiesTaskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(GamersBug.getPlugin(), new Runnable() {

            @Override
            public void run() {

                for(ServerCategoryObject serverCategoryEntity : serverCategoryEntitiesObjectList) {

                    if(serverCategoryEntity.isEntitySet()) {

                        serverCategoryEntity.getEntity().teleport(serverCategoryEntity.getLocation());

                    }

                }

            }

        }, 0L, 1L);

        return null;

    }

}
