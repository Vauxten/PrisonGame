package prisongame.prisongame.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import prisongame.prisongame.PrisonGame;

public class HelloCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.GRAY + "Hello! " + ChatColor.GOLD + "You're currently playing on " + ChatColor.BLUE + "PrisonButBad.minehut.gg" + ChatColor.RED + ", You're on the " + ChatColor.WHITE + PrisonGame.active.name + " map, " + ChatColor.DARK_GREEN + " with " + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + " players online. " + ChatColor.GRAY + "(PrisonButBad made by agmass)");
        return true;
    }
}