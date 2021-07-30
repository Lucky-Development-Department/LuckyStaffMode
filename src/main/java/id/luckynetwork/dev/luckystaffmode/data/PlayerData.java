package id.luckynetwork.dev.luckystaffmode.data;

import id.luckynetwork.dev.luckystaffmode.LuckyStaffMode;
import id.luckynetwork.dev.luckystaffmode.utils.LastInventory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@Getter
@Setter
public class PlayerData {

    private final LuckyStaffMode plugin;
    private final Player player;
    private boolean frozen = false;
    private boolean staffMode = false;
    private LastInventory lastInventory = null;

    public void save() {
        plugin.getCacheManager().savePlayerData(player, this);
    }
}
