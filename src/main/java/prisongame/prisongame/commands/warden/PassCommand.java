package prisongame.prisongame.commands.warden;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.Role;

public class PassCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase(sender.getName())) {
                sender.sendMessage(Color.fromRGB(255, 59, 98) + "You cannot pass to yourself.");
                return true;
            }

            Player g = Bukkit.getPlayer(args[0]);

            if (g == null || !g.isOnline()) {
                sender.sendMessage(ChatColor.BLUE + "We had troubles promoting this player.");
                return true;
            }

            PrisonGame.askType.put(g, -1);
            sender.sendMessage(ChatColor.AQUA + "Succesfully asked player to be the warden!");
            g.sendMessage(Color.fromRGB(255, 59, 98) + "The wardens wants you to be the warden! use '/accept'");
        }
        return true;
    }
}