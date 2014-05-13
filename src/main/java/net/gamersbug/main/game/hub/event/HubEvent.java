package net.gamersbug.main.game.hub.event;

import net.gamersbug.main.GamersBug;
import net.gamersbug.main.MessageManager;
import net.gamersbug.main.game.hub.ServerSelector;
import net.gamersbug.main.model.ServerModel;
import net.gamersbug.main.model.ServersModel;
import net.gamersbug.main.object.ServerObject;
import net.gamersbug.main.util.IconMenuUtil;
import net.gamersbug.main.util.ServersUtil;
import net.gamersbug.main.util.TeamUtil;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class HubEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        TeamUtil.addPlayerToTeam(TeamUtil.getTeamPlayingWithLeastPlayers(), player, false);

    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {

        if(event.getRightClicked() instanceof Villager) {

            Villager villager = (Villager) event.getRightClicked();

            ServerSelector.openServerSelector(villager.getCustomName(), event.getPlayer());

            event.setCancelled(true);

        }

    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {

        if(event.getEntity() instanceof Villager) {

            event.setCancelled(true);

        }

    }

}
