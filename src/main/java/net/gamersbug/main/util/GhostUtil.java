package net.gamersbug.main.util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

public class GhostUtil {

    public static Team ghostTeam;

    private static Set<UUID> ghosts = new HashSet<>();

    public static void enableGhosts() {

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        ghostTeam = board.registerNewTeam("ghosts");

        ghostTeam.setCanSeeFriendlyInvisibles(true);

        ghostTeam.setAllowFriendlyFire(true);

    }

    public static void disableGhosts() {

        clearPlayers();

    }

    public static boolean isGhost(Player player) {

        return (ghosts.contains(player.getUniqueId()));

    }

    public static void setGhost(Player player, boolean isGhost) {

       addPlayer(player);

       if(isGhost) {

           player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 15));

           ghosts.add(player.getUniqueId());

       }else{

           player.removePotionEffect(PotionEffectType.INVISIBILITY);

           ghosts.remove(player.getUniqueId());

       }

    }

    public static void clearGhosts() {

        for(UUID ghostUUID : ghosts) {

            setGhost(Bukkit.getPlayer(ghostUUID), false);

        }

    }

    public static void addPlayer(Player player) {

        if(!ghostTeam.hasPlayer(player)) {

            Bukkit.getLogger().log(Level.INFO, player.getName() + " has been added to the ghosts team.");

            ghostTeam.addPlayer(player);

        }

    }

    public static void addPlayers(Player players[]) {

        for(Player player : players) {

            addPlayer(player);

        }

    }

    public static void removePlayer(Player player) {

        if(ghostTeam.hasPlayer(player)) {

            if(isGhost(player)) {

                setGhost(player, false);

            }

            ghostTeam.removePlayer(player);

        }

    }

    public static void clearPlayers() {

        clearGhosts();

        for(OfflinePlayer player : ghostTeam.getPlayers()) {

            if(player.isOnline()) {

                Player onlinePlayer = (Player) player;

                removePlayer(onlinePlayer);

            }else{

                ghostTeam.removePlayer(player);

            }

        }

    }

}
