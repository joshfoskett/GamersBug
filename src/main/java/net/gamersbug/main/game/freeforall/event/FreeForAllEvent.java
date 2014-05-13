package net.gamersbug.main.game.freeforall.event;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import net.gamersbug.main.config.MapConfig;
import net.gamersbug.main.game.freeforall.FreeForAll;
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
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.ScoreboardManager;

public class FreeForAllEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        ScoreboardUtil.setScoreboardForPlayer(player);

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if(!FreeForAll.hasGameStarted() && TeamUtil.isPlayerPlaying(player)) {

            if(event.getFrom().getX() != event.getTo().getX() || event.getFrom().getY() != event.getTo().getY() || event.getFrom().getZ() != event.getTo().getZ()) {

                player.teleport(event.getFrom());

            }

        }

    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {

        if(!FreeForAll.hasGameStarted() || FreeForAll.hasGameEnded()) {

            if (event.getEntity() instanceof Player) {

                event.setCancelled(true);

            }

        }

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        if(event.getEntity().getKiller() != null) {

            Player killer = event.getEntity().getKiller();

            UUID playerUUID = killer.getUniqueId();

            Integer totalKills = FreeForAll.getInstance().getPlayerKills().get(playerUUID) + 1;

            ScoreboardUtil.setScore(killer, totalKills);

            if(totalKills >= FreeForAll.getInstance().getKillLimit()) {

                FreeForAll.getInstance().setWinner(killer);

            }else{

                FreeForAll.getInstance().getPlayerKills().put(playerUUID, totalKills);

            }

        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        FreeForAll.getInstance().getPlayerKills().remove(player.getUniqueId());

        TeamUtil.removePlayerFromAllTeams(player);

        if(TeamUtil.getPlayersPlaying().size() == 1) {

            Player winner = TeamUtil.getPlayersPlaying().get(0);

            FreeForAll.getInstance().setWinner(winner);

        }

        ScoreboardUtil.removeScore(player);

    }

}
