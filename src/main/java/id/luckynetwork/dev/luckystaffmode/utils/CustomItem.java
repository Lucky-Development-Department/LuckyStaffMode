package id.luckynetwork.dev.luckystaffmode.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class CustomItem {

    private final ItemStack item;
    private final int slot;
    private Callable callable;

    public interface Callable {
        void onPlayerInteract(PlayerInteractEvent event);
        void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event);
    }
}
