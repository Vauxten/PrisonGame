package prisongame.prisongame.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import prisongame.prisongame.PrisonGame;

public class PlayerCommandPreprocessListener implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        var player = event.getPlayer();
        var command = event.getMessage().toLowerCase();

        if (!command.startsWith("/report ") || !command.contains("&k"))
            return;

        player.sendMessage(PrisonGame.mm.deserialize("<red>You cannot use obfuscation."));
        event.setCancelled(true);
    }
}
