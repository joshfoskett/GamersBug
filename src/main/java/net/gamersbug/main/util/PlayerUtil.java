package net.gamersbug.main.util;

import net.gamersbug.main.KitManager;
import net.gamersbug.main.config.ref.Team;
import org.bukkit.*;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.kitteh.tag.TagAPI;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class PlayerUtil {
    
    private static Boolean playerVisibility = false;

    public static void refreshDisplayName(Player player) {

        Team playersTeam = TeamUtil.getPlayersTeam(player);

        String playerName = playersTeam.getChatColor() + player.getName() + ChatColor.WHITE;

        player.setDisplayName(playerName);

        player.setPlayerListName(playerName.length() > 16 ? playerName.substring(0, 16) : playerName);

        TagAPI.refreshPlayer(player);

    }
    
    public static void teleportPlayer(Player player) {

        resetPlayer(player);

        if(!WorldUtil.isCurrentWorldLobby()) {

            giveKit(player);

        }
        
        player.teleport(SpawnUtil.getSpawnLocation(player));
        
    }

    public static void giveKit(Player player) {

        Team playersTeam = TeamUtil.getPlayersTeam(player);

        if(playersTeam.hasKit()) {

            Map<Integer, ItemStack> kitMap = KitManager.getKit(playersTeam.getKitName());

            for(Integer itemSlot : kitMap.keySet()) {

                ItemStack itemStack = kitMap.get(itemSlot);

                switch(itemSlot) {

                    case 100: player.getInventory().setBoots(     itemStack); break;
                    case 101: player.getInventory().setLeggings(  itemStack); break;
                    case 102: player.getInventory().setChestplate(itemStack); break;
                    case 103: player.getInventory().setHelmet(    itemStack); break;

                    default:  player.getInventory().setItem(itemSlot, itemStack); break;
                }

            }

        }

    }
    
    public static void resetPlayer(Player player) {
        
        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
        
        player.getInventory().clear();
        
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        
        for(PotionEffect effect : player.getActivePotionEffects()) {
 
            player.removePotionEffect(effect.getType());

        }

        player.setFireTicks(0);
        
        player.setFoodLevel(20); 
        player.setHealth(20);

        player.setLevel(0);
        
    }
    
    public static Boolean isPlayerVisibilityEnabled() {
        
        return playerVisibility;
        
    }
    
    public static void enablePlayerVisibility() {
        
        playerVisibility = true;
        
        for(Player spectatorPlayer : TeamUtil.getTeam("spectators").getPlayers()) {
            
            setPlayerVisibilityToSpectating(spectatorPlayer);
            
        }
        
        for(Player playingPlayer : TeamUtil.getPlayersPlaying()) {
            
            setPlayerVisibilityToPlaying(playingPlayer);
            
        }
        
    }
    
    public static void disablePlayerVisibility() {
        
        playerVisibility = false;
        
        for(Player player : Bukkit.getOnlinePlayers()) {
            
            player.setAllowFlight(false);
            player.setFlying(false);
            
            for(Player otherPlayer : Bukkit.getOnlinePlayers()) {
                
                if(!player.canSee(otherPlayer)) {
                    
                    player.showPlayer(otherPlayer);
                    
                }

            }
            
        }
        
    }
    
    public static void setPlayerVisibilityToPlaying(Player player) {
        
        if(!isPlayerVisibilityEnabled()) {
            
            return;
            
        }
        
        player.setAllowFlight(false);
        player.setFlying(false);
        
        for(Player otherPlayer : Bukkit.getOnlinePlayers()) {
            
            otherPlayer.showPlayer(player);
            
        }
        
        for(Player spectatorPlayer : TeamUtil.getTeam("spectators").getPlayers()) {
            
            player.hidePlayer(spectatorPlayer);
            
        }
        
    }
    
    public static void setPlayerVisibilityToSpectating(Player player) {
        
        if(!isPlayerVisibilityEnabled()) {
            
            return;
            
        }
        
        player.setAllowFlight(true);
        player.setFlying(true);
        
        for(Player playingPlayer : TeamUtil.getPlayersPlaying()) {
            
            playingPlayer.hidePlayer(player);
            
        }
        
        for(Player spectatorPlayer : TeamUtil.getTeam("spectators").getPlayers()) {
            
            player.showPlayer(spectatorPlayer);
            
        }
        
    }

    public static String createDeathMessage(PlayerDeathEvent event) {

        String deathMessage = "";

        Player prey     = event.getEntity();
        Player predator = prey.getKiller();

        EntityDamageEvent.DamageCause cause = prey.getLastDamageCause().getCause();

        if(cause.equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {

            Entity e = event.getEntity();
            EntityDamageEvent damage = e.getLastDamageCause();

            if(damage instanceof EntityDamageByEntityEvent) {

                EntityDamageByEntityEvent entity = (EntityDamageByEntityEvent) damage;

                if (entity.getDamager() instanceof Player) {

                    if (predator.getItemInHand().getType().equals(Material.AIR)) {

                        deathMessage = prey.getDisplayName() + " was killed by " +  predator.getDisplayName() + " with their bare hands.";

                    }else{

                        Material weapon = predator.getItemInHand().getType();

                        deathMessage = prey.getDisplayName() + " was killed by " +  predator.getDisplayName() + " with a " + weapon.toString().toLowerCase().replace("_", " ") + ".";

                    }

                }else{

                    if (entity.getDamager() instanceof Creature) {

                        deathMessage = prey.getDisplayName() + " was slain by a " + entity.getDamager().getType().toString().toLowerCase().replace("_", " ") + ".";

                    }

                }

            }

        }else if(cause.equals(EntityDamageEvent.DamageCause.PROJECTILE)) {

            deathMessage = prey.getDisplayName() + " was shot to death by " +  predator.getDisplayName() +  ".";

        }else if(cause.equals(EntityDamageEvent.DamageCause.VOID)) {

            deathMessage = prey.getDisplayName() + " fell out of the world.";

        }else if(cause.equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) {

            deathMessage = prey.getDisplayName() + " exploded.";

        }else if(cause.equals(EntityDamageEvent.DamageCause.CONTACT)) {

            deathMessage = prey.getDisplayName() + " ran into a cactus and died.";

        }else if(cause.equals(EntityDamageEvent.DamageCause.DROWNING)) {

            deathMessage = prey.getDisplayName() + " drowned to death.";

        }else if(cause.equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)) {

            deathMessage = prey.getDisplayName() + " hugged a creeper.";

        }else if(cause.equals(EntityDamageEvent.DamageCause.FALL)) {

            deathMessage = prey.getDisplayName() + " fell to their death.";

        }else if(cause.equals(EntityDamageEvent.DamageCause.FALLING_BLOCK)) {

            deathMessage = prey.getDisplayName() + " died due to a falling object.";

        }else if(cause.equals(EntityDamageEvent.DamageCause.FIRE)) {

            deathMessage = prey.getDisplayName() + " was killed by fire.";

        }else if(cause.equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {

            deathMessage = prey.getDisplayName() + " burned to death.";

        }else if(cause.equals(EntityDamageEvent.DamageCause.LAVA)) {

            deathMessage = prey.getDisplayName() + " fell in some lava.";

        }else if(cause.equals(EntityDamageEvent.DamageCause.LIGHTNING)) {

            deathMessage = prey.getDisplayName() + " was stroke by lightning.";

        }else if(cause.equals(EntityDamageEvent.DamageCause.MAGIC)) {

            deathMessage = prey.getDisplayName() + " was killed by dark magic.";

        }else if(cause.equals(EntityDamageEvent.DamageCause.MELTING)) {

            deathMessage = prey.getDisplayName() + " melted away.";

        }else if(cause.equals(EntityDamageEvent.DamageCause.POISON)) {

            deathMessage = prey.getDisplayName() + " was poisoned.";

        }else if(cause.equals(EntityDamageEvent.DamageCause.STARVATION)) {

            deathMessage = prey.getDisplayName() + " forgot to eat.";

        }else if(cause.equals(EntityDamageEvent.DamageCause.SUFFOCATION)) {

            deathMessage = prey.getDisplayName() + " forgot to breathe.";

        }else if(cause.equals(EntityDamageEvent.DamageCause.SUICIDE)) {

            deathMessage = prey.getDisplayName() + " killed them self.";

        }else{

            deathMessage = prey.getDisplayName() + " died due to unknown causes.";

        }

        return deathMessage;

    }
    
}
