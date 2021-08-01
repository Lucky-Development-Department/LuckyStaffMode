package id.luckynetwork.dev.luckystaffmode.commands;

import com.google.common.base.Joiner;
import id.luckynetwork.dev.luckystaffmode.LuckyStaffMode;
import id.luckynetwork.dev.luckystaffmode.handlers.StaffModeHandler;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class StaffModeCommand implements CommandExecutor {

    private final LuckyStaffMode plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        if (!sender.hasPermission("luckystaff.staff")) {
            sender.sendMessage("§e§lSTAFFMODE §a/ §cYou don't have the required permission to execute this command!");
            return false;
        }

        Player player = (Player) sender;
        if (args.length < 1) {
            StaffModeHandler.toggleStaffMode(player, true);
            return false;
        }

        if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("§8§m-------------------------------------------");
            sender.sendMessage("                          §e§lSTAFFMODE COMMANDS");
            sender.sendMessage("");
            sender.sendMessage("§c/staffmode - to enable and disable.");
            sender.sendMessage("§c/staffmode <player> - to activate on target player.");
            sender.sendMessage("§c/staffmode list - list using staffmode.");
            sender.sendMessage("§8§m-------------------------------------------");
            return false;
        }

        if (args[0].equalsIgnoreCase("list")) {
            sender.sendMessage("§e§lSTAFFMODE §a/ §eStaffMode players: §d" + Joiner.on(", ").join(plugin.getCacheManager().getStaffModePlayers()));

            if (plugin.getCacheManager().getStaffModePlayers().isEmpty()) {
                sender.sendMessage("§e§lSTAFFMODE §a/ §cCannot find player using staffmode!");
            }
            return false;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            sender.sendMessage("§e§lSTAFFMODE §a/ §cPlayer not found!");
            return false;
        }

        StaffModeHandler.toggleStaffMode(targetPlayer, true);
        return false;
    }
}
