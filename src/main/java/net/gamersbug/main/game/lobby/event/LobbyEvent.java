package net.gamersbug.main.game.lobby.event;

import net.gamersbug.main.MessageManager;
import net.gamersbug.main.game.lobby.Lobby;
import net.gamersbug.main.util.GameUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LobbyEvent implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Integer totalPlayersOnline = Bukkit.getOnlinePlayers().length;

        if(totalPlayersOnline >= GameUtil.getMinPlayers() && !Lobby.getLobby().isTimerStarted()) {

            MessageManager.broadcastGameMessage("&aThere are enough players to begin now.");

            Lobby.getLobby().startCountdownTimer();

        }

        if(totalPlayersOnline < GameUtil.getMinPlayers() && !Lobby.getLobby().isTimerStarted()) {

            Integer totalMorePlayersNeeded = GameUtil.getMinPlayers() - totalPlayersOnline;

            MessageManager.broadcastGameMessage("&cIn order to start the next game, we need &b" + totalMorePlayersNeeded + "&c more players.");

        }

    }
    
}
