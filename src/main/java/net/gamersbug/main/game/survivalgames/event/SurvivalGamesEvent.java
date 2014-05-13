package net.gamersbug.main.game.survivalgames.event;

import java.util.List;
import java.util.logging.Level;

import net.gamersbug.main.config.MapConfig;
import net.gamersbug.main.game.survivalgames.ChestManager;
import net.gamersbug.main.game.survivalgames.SurvivalGames;
import net.gamersbug.main.util.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.ScoreboardManager;

public class SurvivalGamesEvent implements Listener {
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        
        if(!SurvivalGames.hasGameStarted() && TeamUtil.isPlayerPlaying(player)) {
            
            if(event.getFrom().getX() != event.getTo().getX() || event.getFrom().getY() != event.getTo().getY() || event.getFrom().getZ() != event.getTo().getZ()) {

                player.teleport(event.getFrom());
                
            }
            
        }
        
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();

        if(TeamUtil.isPlayerPlaying(player)) {

            SurvivalGames.getInstance().setPlayerDeath();

            TeamUtil.addPlayerToTeam("spectators", player, true);

        }

        player.getWorld().spawnEntity(player.getLocation(), EntityType.LIGHTNING);

        SurvivalGames.getInstance().checkForWinner();

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        if(TeamUtil.isPlayerPlaying(player)) {

            SurvivalGames.getInstance().setPlayerDeath();

        }

        TeamUtil.removePlayerFromAllTeams(player);
        
        SurvivalGames.getInstance().checkForWinner();
        
    }
    
    @EventHandler    
    public void onPlayerInteract(PlayerInteractEvent event) {

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getState() instanceof Chest) {
            
            Chest chest = (Chest) event.getClickedBlock().getState();
            
            if(!ChestManager.hasChestBeenAccessed(chest.getLocation())) {
                
                if(chest.getInventory().getContents() != null) {
                    
                    chest.getInventory().clear();
                    
                }
                
                BlockFace[] blocksAsideChest = new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
                
                for(BlockFace blockFace : blocksAsideChest) {
                    
                    Block blockAsideChest = chest.getBlock().getRelative(blockFace, 1);
                    
                    if(blockAsideChest.getType() == Material.CHEST) {
                        
                        ChestManager.hasChestBeenAccessed(blockAsideChest.getLocation());
                        
                    }
                    
                }
                
                List<String> chestItems = MapConfig.getGameConfig().getStringList("game.chestitems");
                
                Integer amountOfItems = MiscUtil.randInt(3, 6);
                
                for(int i = 0; i < amountOfItems; i++) {
                    
                    Integer randomChestItemId = MiscUtil.randInt(0, chestItems.size() - 1),
                            randomChestSlotId = MiscUtil.randInt(0, 26);
                    
                    String chestItemString = chestItems.get(randomChestItemId).replace(" ", "_").toUpperCase();
                    
                    Material chestItem = Material.valueOf(chestItemString);
                    
                    chest.getInventory().setItem(randomChestSlotId, new ItemStack(chestItem, 1));
                    
                }
        
            }
            
        }

    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        
        if(event.getBlock().getType() != Material.LEAVES) {
        
            event.setCancelled(true);
        
        }
        
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        
        event.setCancelled(true);
        
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        
        if(event.getCause() == DamageCause.LIGHTNING) {

            event.setCancelled(true);
            
        }
        
    }
    
}
