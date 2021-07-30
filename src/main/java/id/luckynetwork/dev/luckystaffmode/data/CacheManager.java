package id.luckynetwork.dev.luckystaffmode.data;

import id.luckynetwork.dev.luckystaffmode.LuckyStaffMode;
import id.luckynetwork.dev.luckystaffmode.handlers.LyraTasks;
import id.luckynetwork.dev.luckystaffmode.utils.CustomItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter
@AllArgsConstructor
public class CacheManager {

    private LuckyStaffMode plugin;
    private final Map<Player, PlayerData> playerDataMap = new HashMap<>();

    private final List<UUID> tempFrozenPlayersStorage = new ArrayList<>();
    private final Map<ItemStack, CustomItem> customItems = new HashMap<>();

    public PlayerData getPlayerData(Player player) {
        return playerDataMap.getOrDefault(player, new PlayerData(plugin, player));
    }

    public void loadPlayerData(Player player) {
        playerDataMap.put(player, new PlayerData(plugin, player));
    }

    public void savePlayerData(Player player, PlayerData playerData) {
        playerDataMap.put(player, playerData);
    }

    public void destroyPlayerData(Player player) {
        playerDataMap.remove(player);
    }

    public List<Player> getFrozenPlayers() {
        List<Player> frozenPlayers = new ArrayList<>();
        playerDataMap.forEach((player, playerData) -> {
            if (playerData.isFrozen()) {
                frozenPlayers.add(player);
            }
        });

        return frozenPlayers;
    }

    public List<Player> getStaffModePlayers() {
        List<Player> staffModePlayers = new ArrayList<>();
        playerDataMap.forEach((player, playerData) -> {
            if (playerData.isStaffMode()) {
                staffModePlayers.add(player);
            }
        });

        return staffModePlayers;
    }

    public void storeState(UUID uuid) {
        tempFrozenPlayersStorage.add(uuid);
        LyraTasks.runLaterAsync(() -> tempFrozenPlayersStorage.remove(uuid), 60_000L);
    }

    public boolean wasFrozen(UUID uuid) {
        return tempFrozenPlayersStorage.contains(uuid);
    }

    public CustomItem getCustomItem(ItemStack item) {
        return customItems.getOrDefault(item, null);
    }
}
