package id.luckynetwork.dev.luckystaffmode.listeners;

import id.luckynetwork.dev.luckystaffmode.LuckyStaffMode;
import id.luckynetwork.dev.luckystaffmode.data.PlayerData;
import lombok.AllArgsConstructor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

@AllArgsConstructor
public class DamageListener implements Listener {

    private final LuckyStaffMode plugin;

    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            Player victim = (Player) event.getEntity();
            PlayerData playerData = plugin.getCacheManager().getPlayerData(victim);
            if (playerData.isFrozen()) {
                event.setCancelled(true);

                if (event.getDamager().getType() == EntityType.PLAYER) {
                    Player damager = (Player) event.getDamager();
                    damager.sendMessage("§cYou cannot damage " + victim.getName() + " while they're frozen!");
                }
                return;
            }
        }

        if (event.getDamager().getType() == EntityType.PLAYER) {
            Player damager = (Player) event.getDamager();
            if (damager.hasMetadata("vanished")) {
                damager.sendMessage("§6§lWARNING! §6You are currently vanished!");
            }

            PlayerData playerData = plugin.getCacheManager().getPlayerData(damager);
            if (playerData.isFrozen()) {
                event.setCancelled(true);
                damager.sendMessage("§cYou cannot damage others while you're frozen!");
            }
        }
    }

    @EventHandler
    public void onDamage2(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            PlayerData playerData = plugin.getCacheManager().getPlayerData(player);
            if (playerData.isFrozen()) {
                event.setCancelled(true);
            }
        }
    }
}
