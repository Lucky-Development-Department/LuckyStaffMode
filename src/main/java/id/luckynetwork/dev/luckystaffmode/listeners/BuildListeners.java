package id.luckynetwork.dev.luckystaffmode.listeners;

import id.luckynetwork.dev.luckystaffmode.LuckyStaffMode;
import id.luckynetwork.dev.luckystaffmode.data.PlayerData;
import id.luckynetwork.dev.luckystaffmode.utils.CustomItem;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class BuildListeners implements Listener {

    private final LuckyStaffMode plugin;

    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getCacheManager().getPlayerData(player);
        if (playerData.isStaffMode()) {
            ItemStack itemInHand = player.getItemInHand();
            if (itemInHand != null && itemInHand.getType() != Material.AIR) {
                CustomItem customItem = plugin.getCacheManager().getCustomItem(itemInHand);
                if (customItem != null) {
                    event.setCancelled(true);
                    return;
                }
            }

            if (!playerData.isCanBuild()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getCacheManager().getPlayerData(player);
        if (playerData.isStaffMode()) {
            ItemStack itemInHand = player.getItemInHand();
            if (itemInHand != null && itemInHand.getType() != Material.AIR) {
                CustomItem customItem = plugin.getCacheManager().getCustomItem(itemInHand);
                if (customItem != null) {
                    event.setCancelled(true);
                    return;
                }
            }

            if (!playerData.isCanBuild()) {
                event.setCancelled(true);
            }
        }
    }
}
