package id.luckynetwork.dev.luckystaffmode;

import id.luckynetwork.dev.luckystaffmode.commands.FreezeCommand;
import id.luckynetwork.dev.luckystaffmode.commands.StaffModeCommand;
import id.luckynetwork.dev.luckystaffmode.data.CacheManager;
import id.luckynetwork.dev.luckystaffmode.handlers.FreezeHandler;
import id.luckynetwork.dev.luckystaffmode.handlers.StaffModeHandler;
import id.luckynetwork.dev.luckystaffmode.listeners.*;
import id.luckynetwork.dev.luckystaffmode.tasks.FrozenMessageTask;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LuckyStaffMode extends JavaPlugin {

    @Getter
    private static LuckyStaffMode instance;
    private CacheManager cacheManager;
    private List<String> frozenMessage;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.cacheManager = new CacheManager(this);

        StaffModeHandler.loadCustomItems();

        this.registerListener();
        this.getCommand("freeze").setExecutor(new FreezeCommand(this));
        this.getCommand("staffmode").setExecutor(new StaffModeCommand(this));

        this.frozenMessage = this.getConfig().getStringList("freeze.message");
        if (frozenMessage == null) {
            frozenMessage = new ArrayList<>();
        } else {
            frozenMessage = frozenMessage.stream().map(message -> ChatColor.translateAlternateColorCodes('&', message)).collect(Collectors.toList());
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new FrozenMessageTask(this, frozenMessage), 1L, this.getConfig().getInt("freeze.message-delay"));
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        cacheManager.getFrozenPlayers().forEach(it -> FreezeHandler.unfreeze(it, null));
        cacheManager.getStaffModePlayers().forEach(it -> StaffModeHandler.staffModeOff(it, false));
    }

    private void registerListener() {
        this.registerListeners(
                new ConnectionListeners(this),
                new DamageListener(this),
                new InteractListener(this),
                new ItemInventoryListener(this),
                new MoveListener(this)
        );

        if (Bukkit.getPluginManager().getPlugin("SuperVanish") != null || Bukkit.getPluginManager().getPlugin("PremiumVanish") != null) {
            this.registerListeners(new VanishListener(this));
        }
    }

    private void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(it -> Bukkit.getPluginManager().registerEvents(it, this));
    }
}
