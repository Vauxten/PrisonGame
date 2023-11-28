package prisongame.prisongame.commands.staff.debug;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import prisongame.prisongame.MyListener;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.Role;

public class ForceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(PrisonGame.mm.deserialize("<red>Please provide a player."));
            return true;
        }

        var player = Bukkit.getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(PrisonGame.mm.deserialize("<red>That player isn't online."));
            return true;
        }

        if (args.length == 1) {
            sender.sendMessage(PrisonGame.mm.deserialize("<red>Please provide a role."));
            return true;
        }

        try {
            var role = Role.valueOf(args[1].toUpperCase());
            PrisonGame.roles.put(player, role);

            if (role == Role.WARDEN) {
                if (PrisonGame.warden != null)
                    MyListener.playerJoin(PrisonGame.warden, false);

                PrisonGame.wardenCooldown = 60;
                PrisonGame.warden = player;
            }

            sender.sendMessage(PrisonGame.mm.deserialize(
                    "<gray>Forced <white><player></white> to be <white><role></white>.",
                    Placeholder.component("player", player.name()),
                    Placeholder.component("role", Component.text(role.name()))
            ));
        } catch (IllegalArgumentException exception) {
            sender.sendMessage(PrisonGame.mm.deserialize("<red>Invalid role."));
        }

        return true;
    }
}
