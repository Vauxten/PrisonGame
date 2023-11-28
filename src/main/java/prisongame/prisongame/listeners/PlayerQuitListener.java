package prisongame.prisongame.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.Keys;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent event) {
        if (PrisonGame.hardmode.get(event.getPlayer())) {
            Keys.MONEY.set(event.getPlayer(), Keys.BACKUP_MONEY.get(event.getPlayer(), 0.0));
        }
        if (event.getPlayer() == PrisonGame.warden) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "The warden has left the game!");
            PrisonGame.wardenCooldown = 40;
        }
        event.setQuitMessage(ChatColor.GOLD + event.getPlayer().getName() + " walked out through the front door.. (QUIT)");
    }
}
