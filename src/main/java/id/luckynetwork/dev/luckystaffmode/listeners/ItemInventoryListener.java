package id.luckynetwork.dev.luckystaffmode.listeners;

import id.luckynetwork.dev.luckystaffmode.LuckyStaffMode;
import id.luckynetwork.dev.luckystaffmode.data.PlayerData;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class ItemInventoryListener implements Listener {

    private final LuckyStaffMode plugin;

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getCacheManager().getPlayerData(player);
        if (playerData.isFrozen() || playerData.isStaffMode()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryModify(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        PlayerData playerData = plugin.getCacheManager().getPlayerData(player);
        if (playerData.isFrozen() || playerData.isStaffMode()) {
            ItemStack clickedItem = event.getCurrentItem();
            ItemStack cursor = event.getCursor();
            if ((clickedItem != null && plugin.getCacheManager().getCustomItems().containsKey(clickedItem)) || (cursor != null && plugin.getCacheManager().getCustomItems().containsKey(cursor))) {
                event.setCancelled(true);
            }

            if (event.getClick() == ClickType.NUMBER_KEY) {
                ItemStack item = player.getInventory().getItem(event.getHotbarButton());
                if (item != null && plugin.getCacheManager().getCustomItems().containsKey(item)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
