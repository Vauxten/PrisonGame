package prisongame.prisongame.commands.warden;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.Role;

public class PrefixCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String prefix = args[0].toUpperCase();
        Integer prefixlength = 16;
                /*if (((Player) sender).getPersistentDataContainer().getOrDefault(PrisonGame.rank, PersistentDataType.INTEGER, 0) == 1) {
                    prefixlength = 32;
                }*/
        if (prefix.length() <= prefixlength) {
            Player g = (Player) sender;
            g.setCustomName(ChatColor.GRAY + "[" + Color.fromRGB(255, 59, 98) + ChatColor.translateAlternateColorCodes('&', prefix) + " WARDEN" + ChatColor.GRAY + "] " + ChatColor.WHITE + g.getName());
            g.setPlayerListName(ChatColor.GRAY + "[" + Color.fromRGB(255, 59, 98) + ChatColor.translateAlternateColorCodes('&', prefix) + " WARDEN" + ChatColor.GRAY + "] " + ChatColor.WHITE + g.getName());
            g.setDisplayName(ChatColor.GRAY + "[" + Color.fromRGB(255, 59, 98) + ChatColor.translateAlternateColorCodes('&', prefix) + " WARDEN" + ChatColor.GRAY + "] " + ChatColor.WHITE + g.getName());
        } else {
            sender.sendMessage("That's too long!");
        }

        return true;
    }
}