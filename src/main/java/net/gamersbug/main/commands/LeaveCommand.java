package net.gamersbug.main.commands;

import net.gamersbug.main.MessageManager;
import net.gamersbug.main.config.ref.Team;
import net.gamersbug.main.util.PlayerUtil;
import net.gamersbug.main.util.SpawnUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.gamersbug.main.util.TeamUtil;
import net.gamersbug.main.util.WorldUtil;
import org.bukkit.ChatColor;

public class LeaveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {

            return true;

        }

        Player player = (Player)sender;
        
        if(TeamUtil.areTeamsLocked()) {

            MessageManager.playerActionMessage("Teams", "Sorry, but teams are currently locked. Please try to join later.", ChatColor.GRAY, ChatColor.RED, player);
            
        }else if(TeamUtil.isPlayerPlaying(player)) {

            TeamUtil.addPlayerToTeam("spectators", player, true);

            if(!WorldUtil.isCurrentWorldLobby()) {

                PlayerUtil.teleportPlayer(player);

            }

        }else{

            MessageManager.playerActionMessage("Teams", "Sorry, but you have no team to leave.", ChatColor.GRAY, ChatColor.RED, player);

        }

        return true;

    }

}
