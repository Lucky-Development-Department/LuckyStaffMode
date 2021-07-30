package id.luckynetwork.dev.luckystaffmode.listeners;

import id.luckynetwork.dev.luckystaffmode.LuckyStaffMode;
import id.luckynetwork.dev.luckystaffmode.data.PlayerData;
import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

@AllArgsConstructor
public class MoveListener implements Listener {

    private final LuckyStaffMode plugin;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Location from = event.getFrom().clone();
        Location to = event.getTo().clone();

        boolean sameLoc = to.getX() == from.getX() && to.getY() == from.getY() && to.getZ() == from.getZ();
        if (!sameLoc) {
            Player player = event.getPlayer();
            PlayerData playerData = plugin.getCacheManager().getPlayerData(player);
            if (playerData.isFrozen()) {
                from.setYaw(to.getYaw());
                from.setPitch(to.getPitch());

                player.teleport(from);
            }
        }
    }
}
