package prisongame.prisongame.commands.staff;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.lib.Config;

public class PBBReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Config.reload();

        commandSender.sendMessage(ChatColor.GREEN + "Reloaded configuration.");

        return true;
    }
}
