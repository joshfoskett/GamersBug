package net.gamersbug.main.commands;

import net.gamersbug.main.MessageManager;
import net.gamersbug.main.util.GameUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MapCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {

            return true;

        }

        Player player = (Player)sender;

        MessageManager.playerMessage("&4##### &6General Map Info &4#####", player);
        MessageManager.playerMessage("&6Map: &b" + GameUtil.getMapName() + " (" + GameUtil.getGameName() + ")", player);
        MessageManager.playerMessage("&6Objective: &b" + GameUtil.getMapObjective(), player);

        MessageManager.playerMessage("", player);

        MessageManager.playerMessage("&b##### &6Map Creator(s) &b#####", player);
        
        for(String mapCreator : GameUtil.getMapCreators()) {

            MessageManager.playerMessage("&6- &b" + mapCreator, player);
            
        }

        return true;

    }

}
