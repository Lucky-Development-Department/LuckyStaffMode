package id.luckynetwork.dev.luckystaffmode.hooks;

import ga.strikepractice.StrikePractice;
import ga.strikepractice.api.StrikePracticeAPI;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@UtilityClass
public class StrikePracticeHook {

    private StrikePracticeAPI practiceAPI;
    private boolean hooked = false;

    static {
        if (Bukkit.getPluginManager().getPlugin("StrikePractice") != null) {
            try {
                practiceAPI = StrikePractice.getAPI();
                hooked = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void endFight(Player player, String reason) {
        if (!hooked) {
            return;
        }

        if (practiceAPI.isInFight(player) || practiceAPI.isInEvent(player) || practiceAPI.isRanked(player)) {
            practiceAPI.cancelFight(player, reason);
        }
    }

    public Location getSpawn() {
        if (!hooked) {
            return null;
        }

        return practiceAPI.getSpawnLocation();
    }
}
