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
            sender.sendMessage("§e§lFREEZE §a/ §cUsage: /freeze <player>");
            sender.sendMessage("§e§lFREEZE §a/ §cUsage: /freeze list");
            return false;
        }

        if (args[0].equalsIgnoreCase("list")) {
            sender.sendMessage("§e§lFREEZE §a/ §eFrozen players: §d" + Joiner.on(", ").join(plugin.getCacheManager().getFrozenPlayers()));
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
