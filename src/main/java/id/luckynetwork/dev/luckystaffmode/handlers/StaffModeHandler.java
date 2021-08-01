package id.luckynetwork.dev.luckystaffmode.handlers;

import id.luckynetwork.dev.luckystaffmode.LuckyStaffMode;
import id.luckynetwork.dev.luckystaffmode.data.PlayerData;
import id.luckynetwork.dev.luckystaffmode.hooks.PlayerVanishHook;
import id.luckynetwork.dev.luckystaffmode.hooks.StrikePracticeHook;
import id.luckynetwork.dev.luckystaffmode.utils.CustomItem;
import id.luckynetwork.dev.luckystaffmode.utils.ItemBuilder;
import id.luckynetwork.dev.luckystaffmode.utils.LastInventory;
import lombok.experimental.UtilityClass;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.*;

@UtilityClass
public class StaffModeHandler {

    private final LuckyStaffMode plugin = LuckyStaffMode.getInstance();
    private CustomItem vanishedCustomItem;

    public void loadCustomItems() {
        List<CustomItem> customItems = new ArrayList<>();

        customItems.add(new CustomItem(
                new ItemBuilder(Material.COMPASS)
                        .setName("§cTeleporter §7(Right Click)")
                        .addLoreLine("§7Click to teleport across blocks")
                        .toItemStack(),
                6,
                new CustomItem.Callable() {
                    @Override
                    public void onPlayerInteract(PlayerInteractEvent event) {
                        Player player = event.getPlayer();
                        Block targetBlock = player.getTargetBlock((Set<Material>) null, 120);
                        if (targetBlock != null && targetBlock.getType() != Material.AIR) {
                            Location location = targetBlock.getLocation().clone();
                            location.setY(location.getY() + 1.0);
                            location.setPitch(player.getLocation().getPitch());
                            location.setYaw(player.getLocation().getYaw());

                            player.teleport(location);
                        }
                    }

                    @Override
                    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
                    }
                })
        );

        customItems.add(new CustomItem(
                new ItemBuilder(Material.SKULL_ITEM)
                        .setDurability((short) 3)
                        .setName("§eRandom Teleport §7(Right Click)")
                        .addLoreLine("§7Click to teleport to a random player")
                        .toItemStack(),
                2,
                new CustomItem.Callable() {
                    @Override
                    public void onPlayerInteract(PlayerInteractEvent event) {
                        Player player = event.getPlayer();
                        Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[0]);
                        Random random = new Random();

                        Player randomPlayer = null;
                        int failCounter = 0;

                        if (players.length <= 1) {
                            player.sendMessage("§e§lSTAFFMODE §a/ §cCannot find any players to teleport!");
                            return;
                        }

                        while (randomPlayer == null || randomPlayer.getName().equals(player.getName())) {
                            UUID uuid = players[random.nextInt(players.length)].getUniqueId();
                            randomPlayer = Bukkit.getPlayer(uuid);

                            failCounter++;
                            if (failCounter >= 10) {
                                player.sendMessage("§e§lSTAFFMODE §a/ §cFailed to teleport to a random player!");
                                return;
                            }
                        }

                        player.teleport(randomPlayer.getLocation());
                        player.sendMessage("§e§lSTAFFMODE §a/ §aTeleported to " + randomPlayer.getName() + "!");
                    }

                    @Override
                    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
                    }
                })
        );

        customItems.add(new CustomItem(
                new ItemBuilder(Material.DIAMOND)
                        .setName("§6Spawn §7(Right Click)")
                        .addLoreLine("§7Click to teleport to spawn")
                        .toItemStack(),
                3,
                new CustomItem.Callable() {
                    @Override
                    public void onPlayerInteract(PlayerInteractEvent event) {
                        Player player = event.getPlayer();
                        Location spawn = StrikePracticeHook.getSpawn();
                        if (spawn == null) {
                            World world = Bukkit.getWorld(plugin.getConfig().getString("spawn.world"));
                            if (world != null) {
                                spawn = new Location(
                                        world,
                                        plugin.getConfig().getDouble("spawn.x"),
                                        plugin.getConfig().getDouble("spawn.y"),
                                        plugin.getConfig().getDouble("spawn.z"),
                                        Float.parseFloat(plugin.getConfig().getString("spawn.yaw")),
                                        Float.parseFloat(plugin.getConfig().getString("spawn.pitch"))
                                );
                            } else {
                                spawn = player.getWorld().getSpawnLocation();
                            }
                        }

                        player.teleport(spawn);
                    }

                    @Override
                    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
                    }
                })
        );

        customItems.add(new CustomItem(
                new ItemBuilder(Material.ICE)
                        .setName("§bFreeze §7(Right Click)")
                        .addLoreLine("§7Click freeze a player")
                        .toItemStack(),
                5,
                new CustomItem.Callable() {
                    @Override
                    public void onPlayerInteract(PlayerInteractEvent event) {
                    }

                    @Override
                    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
                        Entity rightClicked = event.getRightClicked();
                        if (rightClicked.getType() == EntityType.PLAYER) {
                            Player player = event.getPlayer();
                            Player target = (Player) rightClicked;

                            FreezeHandler.toggleFreeze(target, player);
                        }
                    }
                })
        );

        customItems.add(new CustomItem(
                new ItemBuilder(Material.DIAMOND_SWORD)
                        .setName("§4Sharpness 1000 sword")
                        .addLoreLine("§7Used to kill hackers in an emergency")
                        .addLoreLine("§7Right-click to insta kill(even in sumo!)")
                        .addEnchantment(Enchantment.DAMAGE_ALL, 1000)
                        .hideEnchants()
                        .toItemStack(),
                0,
                new CustomItem.Callable() {
                    @Override
                    public void onPlayerInteract(PlayerInteractEvent event) {
                    }

                    @Override
                    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
                        Entity rightClicked = event.getRightClicked();
                        if (rightClicked instanceof LivingEntity) {
                            LivingEntity target = (LivingEntity) rightClicked;
                            target.setHealth(0);
                        }
                    }
                })
        );

        if (PlayerVanishHook.hooked) {
            ItemStack unvanishedItem = new ItemBuilder(Material.INK_SACK)
                    .setDurability((short) 8)
                    .setName("§cToggle Vanish(Off)")
                    .toItemStack();
            ItemStack vanishedItem = new ItemBuilder(Material.INK_SACK)
                    .setDurability((short) 10)
                    .setName("§aToggle Vanish(On)")
                    .toItemStack();

            customItems.add(new CustomItem(
                    unvanishedItem,
                    8,
                    new CustomItem.Callable() {
                        @Override
                        public void onPlayerInteract(PlayerInteractEvent event) {
                            Player player = event.getPlayer();
                            player.setMetadata("TEMP_VTOGGLE", new FixedMetadataValue(plugin, true));
                            PlayerVanishHook.hide(player);

                            player.getInventory().setItem(8, vanishedItem);
                            player.updateInventory();
                        }

                        @Override
                        public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
                        }
                    })
            );
            vanishedCustomItem = new CustomItem(
                    vanishedItem,
                    8,
                    new CustomItem.Callable() {
                        @Override
                        public void onPlayerInteract(PlayerInteractEvent event) {
                            Player player = event.getPlayer();
                            player.setMetadata("TEMP_VTOGGLE", new FixedMetadataValue(plugin, true));
                            PlayerVanishHook.show(player);

                            player.getInventory().setItem(8, unvanishedItem);
                            player.updateInventory();
                        }

                        @Override
                        public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
                        }
                    });

            customItems.add(vanishedCustomItem);
        }

        customItems.forEach(customItem -> plugin.getCacheManager().getCustomItems().put(customItem.getItem(), customItem));
    }


    public void toggleStaffMode(Player player, boolean toggleVanish) {
        PlayerData playerData = plugin.getCacheManager().getPlayerData(player);
        if (!playerData.isStaffMode()) {
            StaffModeHandler.staffModeOn(player, toggleVanish);
        } else {
            StaffModeHandler.staffModeOff(player, toggleVanish);
        }
    }

    public void staffModeOn(Player player, boolean vanish) {
        PlayerData playerData = plugin.getCacheManager().getPlayerData(player);

        PlayerInventory inventory = player.getInventory();
        LastInventory lastInventory = new LastInventory(inventory.getContents(), inventory.getArmorContents(), player.getGameMode());

        playerData.setLastInventory(lastInventory);

        inventory.clear();
        inventory.setArmorContents(null);
        plugin.getCacheManager().getCustomItems().values()
                .forEach(customItem -> inventory.setItem(customItem.getSlot(), customItem.getItem()));

        player.updateInventory();
        player.setGameMode(GameMode.CREATIVE);

        if (vanish) {
            PlayerVanishHook.hide(player);
            playerData.setStaffMode(true);
        }
        player.sendMessage("§e§lSTAFFMODE §a/ §eStaffMode §aenabled");

        playerData.save();
    }

    public void staffModeOff(Player player, boolean unvanish) {
        PlayerData playerData = plugin.getCacheManager().getPlayerData(player);

        LastInventory lastInventory = playerData.getLastInventory();
        if (lastInventory != null) {
            player.getInventory().setContents(lastInventory.getContents());
            player.getInventory().setArmorContents(lastInventory.getArmorContents());
            player.setGameMode(lastInventory.getGameMode());

            player.updateInventory();
            playerData.setLastInventory(null);

        }

        if (unvanish) {
            PlayerVanishHook.show(player);
            playerData.setStaffMode(false);
        }
        player.sendMessage("§e§lSTAFFMODE §a/ §eStaffMode §cdisabled");

        playerData.save();
    }
}
