package id.luckynetwork.dev.luckystaffmode.listeners;

import id.luckynetwork.dev.luckystaffmode.LuckyStaffMode;
import id.luckynetwork.dev.luckystaffmode.data.PlayerData;
import id.luckynetwork.dev.luckystaffmode.utils.CustomItem;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class InteractListener implements Listener {

    private final LuckyStaffMode plugin;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getCacheManager().getPlayerData(player);
        if (playerData.isStaffMode()) {
            ItemStack itemInHand = player.getItemInHand();
            if (itemInHand != null && itemInHand.getType() != Material.AIR) {
                CustomItem customItem = plugin.getCacheManager().getCustomItem(itemInHand);
                if (customItem != null) {
                    customItem.getCallable().onPlayerInteract(event);
                }
            }
        }
    }

    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getCacheManager().getPlayerData(player);
        if (playerData.isStaffMode()) {
            ItemStack itemInHand = player.getItemInHand();
            if (itemInHand != null && itemInHand.getType() != Material.AIR) {
                CustomItem customItem = plugin.getCacheManager().getCustomItem(itemInHand);
                if (customItem != null) {
                    customItem.getCallable().onPlayerInteractAtEntity(event);
                }
            }
        }
    }
}
