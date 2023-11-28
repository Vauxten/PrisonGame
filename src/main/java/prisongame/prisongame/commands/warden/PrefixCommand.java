package prisongame.prisongame.commands.warden;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.Config;
import prisongame.prisongame.lib.Role;

public class PrefixCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String prefix = args[0].toUpperCase();
        Integer prefixlength = 16;
                /*if (((Player) sender).getPersistentDataContainer().getOrDefault(PrisonGame.rank, PersistentDataType.INTEGER, 0) == 1) {
                    prefixlength = 32;
                }*/

        var plainText = prefix.replaceAll("(?i)&+[a-f0-9kl-or]+", "").toLowerCase();
        System.out.println(plainText);
        for (String container : Config.Warden.Prefix.bannedContainers) {
            System.out.println(plainText.toLowerCase());
            if (plainText.contains(container.toLowerCase())) {
                sender.sendMessage(PrisonGame.mm.deserialize("<red>You cannot set that prefix."));
                return true;
            }
        }

        if (prefix.length() <= prefixlength) {
            Player g = (Player) sender;
            g.setCustomName(ChatColor.GRAY + "[" + ChatColor.RED + ChatColor.translateAlternateColorCodes('&', prefix) + " WARDEN" + ChatColor.GRAY + "] " + ChatColor.WHITE + g.getName());
            g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + ChatColor.translateAlternateColorCodes('&', prefix) + " WARDEN" + ChatColor.GRAY + "] " + ChatColor.WHITE + g.getName());
            g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.RED + ChatColor.translateAlternateColorCodes('&', prefix) + " WARDEN" + ChatColor.GRAY + "] " + ChatColor.WHITE + g.getName());
        } else {
            sender.sendMessage("That's too long!");
        }

        return true;
    }
}