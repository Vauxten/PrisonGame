package prisongame.prisongame.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import prisongame.prisongame.FilteredWords;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.Keys;
import prisongame.prisongame.lib.UwUtils;
import prisongame.prisongame.lib.Role;

public class AsyncPlayerChatListener implements Listener {
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (event.getMessage().equals("1775182")) {
            event.setCancelled(true);
            return;
        }
        if (PrisonGame.warden == event.getPlayer()) {
            if (!PrisonGame.word.get(event.getPlayer()).equals(event.getMessage()) && !event.getMessage().toLowerCase().contains("&k")) {
                Bukkit.getLogger().info(event.getPlayer().getDisplayName() + ChatColor.RED + ": " + FilteredWords.filtermsg(event.getMessage()));
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!Keys.NO_WARDEN_SPACES.has(p)) {
                        p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1);
                    }


                    //if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.rank, PersistentDataType.INTEGER, 0) != 1)
                    //    p.sendMessage(event.getPlayer().getPlayerListName() + ChatColor.RED + ": " + ChatColor.RED + FilteredWords.filtermsg(event.getMessage()));
                    //else {

                    event.setFormat("%1$s" + ChatColor.RED + ": %2$s");
                    event.setMessage(ChatColor.RED + ChatColor.translateAlternateColorCodes('&', FilteredWords.filtermsg(event.getMessage())));
                    //}
                }

            } else {
                event.getPlayer().sendMessage(ChatColor.RED + "Do not spam!");
                event.setCancelled(true);
            }
        } else {
            if (!event.getPlayer().getDisplayName().contains("SOLITARY")) {
                if (PrisonGame.warden != event.getPlayer())
                    //if (event.getPlayer().getPersistentDataContainer().getOrDefault(PrisonGame.rank, PersistentDataType.INTEGER, 0) != 1)
                    //    Bukkit.broadcastMessage(event.getPlayer().getPlayerListName() + ChatColor.GRAY + ": " + ChatColor.GRAY + ChatColor.GRAY + FilteredWords.filtermsg(event.getMessage()));
                    //else {
                    if (event.getMessage().toLowerCase().equals("piggopet reference")) {
                        PrisonGame.givepig = true;
                    }
                if (!PrisonGame.word.getOrDefault(event.getPlayer(), "").equals(event.getMessage())) {
                    if (!PrisonGame.chatmuted) {
                        event.setFormat("%1$s" + ChatColor.GRAY + ": %2$s");
                        event.setMessage(FilteredWords.filtermsg(event.getMessage()));
                        if (PrisonGame.roles.get(event.getPlayer()) == Role.NURSE && PrisonGame.FEMBOYS) {
                            event.setMessage(UwUtils.uwuify(event.getMessage()));
                        }
                        if (PrisonGame.roles.get(event.getPlayer()) != Role.PRISONER && PrisonGame.grammar) {
                            String b = event.getMessage();
                            b = b.substring(0, 1).toUpperCase() + b.substring(1);
                            b.replace(" i ", " I ");
                            if (!b.endsWith(".") && !b.endsWith("!") && !b.endsWith("?"))
                                b += ".";
                            event.setMessage(FilteredWords.filtermsg(b));
                        }
                        PrisonGame.word.put(event.getPlayer(), event.getMessage());
                    } else {
                        event.setCancelled(true);
                        if (PrisonGame.roles.get(event.getPlayer()) == Role.PRISONER) {
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (PrisonGame.roles.get(p) == Role.PRISONER) {
                                    p.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER CHAT" + ChatColor.GRAY + "] " + ChatColor.WHITE + event.getPlayer().getName() + ": " + FilteredWords.filtermsg(event.getMessage()));
                                }
                            }
                        }
                        if (PrisonGame.roles.get(event.getPlayer()) != Role.PRISONER) {
                            event.setCancelled(false);
                            event.setFormat("%1$s" + ChatColor.GRAY + ": %2$s");
                            event.setMessage(FilteredWords.filtermsg(event.getMessage()));
                            if (PrisonGame.roles.get(event.getPlayer()) != Role.PRISONER && PrisonGame.grammar) {
                                String b = event.getMessage();
                                b = b.substring(0, 1).toUpperCase() + b.substring(1);
                                b.replace(" i ", " I ");
                                if (!b.endsWith(".") && !b.endsWith("!") && !b.endsWith("?"))
                                    b += ".";
                                event.setMessage(FilteredWords.filtermsg(b));
                            }
                            PrisonGame.word.put(event.getPlayer(), event.getMessage());
                        }
                    }
                } else {
                    event.getPlayer().sendMessage(ChatColor.RED + "Do not spam!");
                    event.setCancelled(true);
                }
            } else {
                if (PrisonGame.warden != event.getPlayer()) {
                    event.setCancelled(true);
                }
            }
        }
    }

}
