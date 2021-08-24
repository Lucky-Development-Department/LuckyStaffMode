package id.luckynetwork.dev.luckystaffmode.listeners;

import de.myzelyam.api.vanish.PlayerHideEvent;
import de.myzelyam.api.vanish.PlayerShowEvent;
import id.luckynetwork.dev.luckystaffmode.LuckyStaffMode;
import id.luckynetwork.dev.luckystaffmode.data.PlayerData;
import id.luckynetwork.dev.luckystaffmode.handlers.LyraTasks;
import id.luckynetwork.dev.luckystaffmode.handlers.StaffModeHandler;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

@AllArgsConstructor
public class VanishListener implements Listener {

    private final LuckyStaffMode plugin;
    private final boolean staffModeOnVanish;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onVanish(PlayerHideEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("luckystaff.staff")) {
            if (player.hasMetadata("TEMP_VTOGGLE")) {
                player.removeMetadata("TEMP_VTOGGLE", plugin);
                return;
            }

            PlayerData playerData = plugin.getCacheManager().getPlayerData(player);
            if (playerData.isStaffMode()) {
                LyraTasks.runLater(() -> StaffModeHandler.refreshInventory(player), 1L);
            }

            if (this.staffModeOnVanish) {
                StaffModeHandler.staffModeOn(player, false);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onUnVanish(PlayerShowEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("luckystaff.staff")) {
            if (player.hasMetadata("TEMP_VTOGGLE")) {
                player.removeMetadata("TEMP_VTOGGLE", plugin);
                return;
            }

            PlayerData playerData = plugin.getCacheManager().getPlayerData(player);
            if (playerData.isStaffMode()) {
                LyraTasks.runLater(() -> StaffModeHandler.refreshInventory(player), 1L);
            }

            if (this.staffModeOnVanish) {
                StaffModeHandler.staffModeOff(player, false);
            }
        }
    }
}
