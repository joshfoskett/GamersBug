package net.gamersbug.main.commands;

import net.gamersbug.main.MessageManager;
import net.gamersbug.main.config.ref.Team;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.gamersbug.main.util.TeamUtil;
import org.bukkit.ChatColor;

public class TeamCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {

            return true;

        }

        Player player = (Player)sender;
        
        Team playersTeam = TeamUtil.getPlayersTeam(player);

        MessageManager.playerActionMessage("Teams", "You are on the " + playersTeam.getNameFormatted() + " .", ChatColor.GOLD, ChatColor.WHITE, player);

        return true;

    }

}
