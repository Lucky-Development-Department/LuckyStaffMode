package id.luckynetwork.dev.luckystaffmode.handlers;

import id.luckynetwork.dev.luckystaffmode.LuckyStaffMode;
import id.luckynetwork.dev.luckystaffmode.data.PlayerData;
import id.luckynetwork.dev.luckystaffmode.hooks.StrikePracticeHook;
import id.luckynetwork.dev.luckystaffmode.utils.Utils;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public class FreezeHandler {

    private final LuckyStaffMode plugin = LuckyStaffMode.getInstance();

    public void toggleFreeze(Player player, @Nullable CommandSender executor) {
        PlayerData playerData = plugin.getCacheManager().getPlayerData(player);
        if (playerData.isFrozen()) {
            FreezeHandler.unfreeze(player, executor);
        } else {
            FreezeHandler.freeze(player, executor);
        }
    }

    public void freeze(Player player, @Nullable CommandSender executor) {
        PlayerData playerData = plugin.getCacheManager().getPlayerData(player);
        playerData.setFrozen(true);
        playerData.save();

        Block highestBlock = player.getWorld().getHighestBlockAt(player.getLocation());
        player.teleport(new Location(highestBlock.getWorld(), highestBlock.getX(), highestBlock.getY(), highestBlock.getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()));
        player.setAllowFlight(true);

        FreezeHandler.alertStaff(executor == null ? "CONSOLE" : executor.getName(), player.getName(), true);
        StrikePracticeHook.endFight(player, "A player is frozen");
        plugin.getFrozenMessage().forEach(player::sendMessage);
    }

    public void unfreeze(Player player, @Nullable CommandSender executor) {
        PlayerData playerData = plugin.getCacheManager().getPlayerData(player);
        playerData.setFrozen(false);
        playerData.save();

        player.setAllowFlight(false);

        FreezeHandler.alertStaff(executor == null ? "CONSOLE" : executor.getName(), player.getName(), false);
    }

    private void alertStaff(String executorName, String targetName, boolean state) {
        String message;
        if (state) {
            message = "§e§lFREEZE §a/ §d" + targetName + " §ehas been §cfrozen §eby §6" + executorName + " §eat §b" + Utils.getFormattedDate();
        } else {
            message = "§e§lFREEZE §a/ §d" + targetName + " §ehas been §aunfrozen §eby §6" + executorName + " §eat §b" + Utils.getFormattedDate();
        }

        Bukkit.getOnlinePlayers().stream().filter(it -> it.hasPermission("luckystaff.staff")).forEach(online -> online.sendMessage(message));
    }
}
