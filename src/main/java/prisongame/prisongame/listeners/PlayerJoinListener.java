package prisongame.prisongame.listeners;

import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;
import prisongame.prisongame.PrisonGame;

import static prisongame.prisongame.MyListener.playerJoin;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (PrisonGame.wardenenabled) {
            Player p = event.getPlayer();
            PrisonGame.trustlevel.put(event.getPlayer(), 0);
            DisguiseAPI.undisguiseToAll(event.getPlayer());
            event.getPlayer().removePotionEffect(PotionEffectType.DARKNESS);
            event.getPlayer().removePotionEffect(PotionEffectType.WEAKNESS);
            event.setJoinMessage(ChatColor.GOLD + event.getPlayer().getName() + " was caught and sent to prison! (JOIN)");
            event.getPlayer().setGameMode(GameMode.ADVENTURE);
            PrisonGame.st.put(event.getPlayer(), 0.0);
            PrisonGame.sp.put(event.getPlayer(), 0.0);
            playerJoin(event.getPlayer(), false);
            if (event.getPlayer().hasPotionEffect(PotionEffectType.WATER_BREATHING)) {
                Player g = event.getPlayer();
                Bukkit.broadcastMessage(ChatColor.GRAY + g.getName() + " was sent to solitary!");
                g.setGameMode(GameMode.ADVENTURE);
                g.removePotionEffect(PotionEffectType.LUCK);
                PrisonGame.solittime.put(g, 20 * 120);
                PrisonGame.escaped.put(g, true);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + g.getName() + " only prison:solit");
                Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                    g.teleport(PrisonGame.active.getSolit());
                }, 10);
                g.sendTitle("", "You're in solitary.", 10, 0, 10);
                g.addPotionEffect(PotionEffectType.WATER_BREATHING.createEffect(Integer.MAX_VALUE, 1));
                Player pe = g;
                pe.setCustomName(ChatColor.GRAY + "[" + ChatColor.BLACK + "SOLITARY" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + pe.getName());
                pe.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.BLACK + "SOLITARY" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + pe.getName());
                pe.setDisplayName(ChatColor.GRAY + "[" + ChatColor.BLACK + "SOLITARY" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + pe.getName());

                event.setJoinMessage(ChatColor.RED + event.getPlayer().getName() + " was caught and sent back to solitary! (JOIN)");
            }
        }else{
            event.getPlayer().kickPlayer(ChatColor.translateAlternateColorCodes('&', "&4&lThe server is currently &a&lReloading &c&lOr &4Completely fucked up. &c&lIf this error is occuring constanly please alert me @ &bhttps://discord.gg/GrcHKkFQsv"));
        }
        Player pe = (Player) event.getPlayer();
        if (PrisonGame.savedPlayerGuards.get(PrisonGame.warden.getUniqueId()).containsKey(pe.getUniqueId())) {
            switch (PrisonGame.savedPlayerGuards.get(PrisonGame.warden.getUniqueId()).get(pe.getUniqueId())) {
                case 2 -> PrisonGame.setNurse((Player) pe);
                case 1 -> PrisonGame.setGuard((Player) pe);
                case 3 -> PrisonGame.setSwat((Player) pe);
                default -> ((Player) pe).sendMessage("An error has occured.");
            }
        }
    }
}
