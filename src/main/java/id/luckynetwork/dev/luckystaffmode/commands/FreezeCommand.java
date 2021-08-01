package id.luckynetwork.dev.luckystaffmode.commands;

import com.google.common.base.Joiner;
import id.luckynetwork.dev.luckystaffmode.LuckyStaffMode;
import id.luckynetwork.dev.luckystaffmode.handlers.FreezeHandler;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class FreezeCommand implements CommandExecutor {

    private final LuckyStaffMode plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("luckystaff.staff")) {
            sender.sendMessage("§e§lFREEZE §a/ §cYou don't have the required permission to execute this command!");
            return false;
        }

        if (args.length < 1) {
            sender.sendMessage("§8§m-------------------------------------------");
            sender.sendMessage("                          §e§lFREEZE COMMANDS");
            sender.sendMessage("");
            sender.sendMessage("§c/freeze <player> - to freeze on target player.");
            sender.sendMessage("§c/freeze list - list using freeze.");
            sender.sendMessage("§8§m-------------------------------------------");
            return false;
        }

        if (args[0].equalsIgnoreCase("list")) {
            sender.sendMessage("§e§lFREEZE §a/ §eFrozen players: §d" + Joiner.on(", ").join(plugin.getCacheManager().getFrozenPlayers()));

            if (plugin.getCacheManager().getStaffModePlayers().isEmpty()) {
                sender.sendMessage("§e§lFREEZE §a/ §cCannot find player using freeze!");
            }
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§e§lFREEZE §a/ §cPlayer not found!");
            return false;
        }

        FreezeHandler.toggleFreeze(target, sender);
        return false;
    }
}
