package prisongame.prisongame.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import prisongame.prisongame.lib.Config;

public class DiscordCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.BLUE + "https://discord.gg/" + Config.General.discordInvite);
        return true;
    }
}
