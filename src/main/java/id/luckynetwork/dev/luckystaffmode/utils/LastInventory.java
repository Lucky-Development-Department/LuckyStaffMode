package id.luckynetwork.dev.luckystaffmode.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@Getter
@Setter
public class LastInventory {

    private ItemStack[] contents;
    private ItemStack[] armorContents;
    private GameMode gameMode;

}