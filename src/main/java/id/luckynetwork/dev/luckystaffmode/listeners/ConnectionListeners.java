package id.luckynetwork.dev.luckystaffmode.listeners;

import id.luckynetwork.dev.luckystaffmode.LuckyStaffMode;
import id.luckynetwork.dev.luckystaffmode.data.PlayerData;
import id.luckynetwork.dev.luckystaffmode.handlers.FreezeHandler;
import id.luckynetwork.dev.luckystaffmode.hooks.PlayerVanishHook;
import id.luckynetwork.dev.luckystaffmode.utils.LastInventory;
import id.luckynetwork.dev.luckystaffmode.utils.Utils;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class ConnectionListeners implements Listener {

    private final LuckyStaffMode plugin;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getCacheManager().loadPlayerData(player);
        if (plugin.getCacheManager().wasFrozen(player.getUniqueId())) {
            plugin.getCacheManager().getTempFrozenPlayersStorage().remove(player.getUniqueId());

            FreezeHandler.freeze(player, null);

            String message = "§e§lFREEZE §a/ §d" + player.getName() + " §alogged in §ewhile frozen at §b" + Utils.getFormattedDate() + "§e!";
            Bukkit.getOnlinePlayers().stream().filter(it -> it.hasPermission("luckystaff.staff")).forEach(it -> it.sendMessage(message));
            return;
        }

        if (player.hasPermission("luckystaff.staff")) {
            PlayerVanishHook.checkOnJoin(player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = plugin.getCacheManager().getPlayerData(player);
        LastInventory lastInventory = playerData.getLastInventory();
        if (lastInventory != null) {
            player.getInventory().setContents(lastInventory.getContents());
            player.getInventory().setArmorContents(lastInventory.getArmorContents());
            player.setGameMode(lastInventory.getGameMode());
        }

        if (playerData.isFrozen()) {
            plugin.getCacheManager().storeState(player.getUniqueId());

            FreezeHandler.unfreeze(player, null);

            String message = "§e§lFREEZE §a/ §d" + player.getName() + " §clogged out §ewhile frozen at §b" + Utils.getFormattedDate() + "§e!";
            Bukkit.getOnlinePlayers().stream().filter(it -> it.hasPermission("luckystaff.staff")).forEach(it -> it.sendMessage(message));
        }

        plugin.getCacheManager().destroyPlayerData(player);
    }
}
