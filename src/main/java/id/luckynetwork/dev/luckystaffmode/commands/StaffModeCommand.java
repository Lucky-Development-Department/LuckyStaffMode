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
            sender.sendMessage("§e§lSTAFFMODE §a/ §cUsage: /staffmode");
            sender.sendMessage("§e§lSTAFFMODE §a/ §cUsage: /staffmode <player>");
            sender.sendMessage("§e§lSTAFFMODE §a/ §cUsage: /staffmode list");
            return false;
        }

        if (args[0].equalsIgnoreCase("list")) {
            sender.sendMessage("§e§lSTAFFMODE §a/ §eStaffMode players: §d" + Joiner.on(", ").join(plugin.getCacheManager().getStaffModePlayers()));
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§e§lSTAFFMODE §a/ §cPlayer not found!");
            return false;
        }

        StaffModeHandler.toggleStaffMode(target, true);
        return false;
    }
}
