package id.luckynetwork.dev.luckystaffmode.hooks;

import de.myzelyam.api.vanish.VanishAPI;
import id.luckynetwork.dev.luckystaffmode.LuckyStaffMode;
import id.luckynetwork.dev.luckystaffmode.data.PlayerData;
import id.luckynetwork.dev.luckystaffmode.handlers.LyraTasks;
import id.luckynetwork.dev.luckystaffmode.handlers.StaffModeHandler;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

@UtilityClass
public class PlayerVanishHook {

    private final LuckyStaffMode plugin;
    private boolean checkOnJoin = true;
    public boolean hooked = false;

    static {
        plugin = LuckyStaffMode.getInstance();
        try {
            hooked = Bukkit.getPluginManager().getPlugin("SuperVanish") != null || Bukkit.getPluginManager().getPlugin("PremiumVanish") != null;
            checkOnJoin = plugin.getConfig().getBoolean("staffmode-check-join", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hide(Player player) {
        if (!hooked) {
            return;
        }

        if (!VanishAPI.isInvisible(player) || !player.hasMetadata("vanished")) {
            player.setMetadata("TEMP_VTOGGLE", new FixedMetadataValue(plugin, true));
            VanishAPI.hidePlayer(player);
        }
    }

    public void show(Player player) {
        if (!hooked) {
            return;
        }

        if (VanishAPI.isInvisible(player) || player.hasMetadata("vanished")) {
            player.setMetadata("TEMP_VTOGGLE", new FixedMetadataValue(plugin, true));
            VanishAPI.showPlayer(player);
        }
    }

    public void checkOnJoin(Player player) {
        if (!hooked) {
            return;
        }

        if (checkOnJoin) {
            if (player.hasPermission("sv.use") || player.hasPermission("pv.use")) {
                LyraTasks.runLaterAsync(() -> {
                    if (Bukkit.getPlayer(player.getUniqueId()) != null) {
                        if (VanishAPI.isInvisible(player) || player.hasMetadata("vanished")) {
                            PlayerData playerData = plugin.getCacheManager().getPlayerData(player);
                            if (!playerData.isStaffMode()) {
                                StaffModeHandler.staffModeOn(player, false);
                            }
                        }
                    }
                }, 1_000);
            }
        }
    }
}
