package prisongame.prisongame.listeners;

import net.minecraft.network.chat.ChatClickable;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffectType;
import prisongame.prisongame.MyListener;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.Keys;
import prisongame.prisongame.lib.Role;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath2(PlayerDeathEvent event) {
        Player p = event.getEntity();
        if(p.isDead()) {
            var killer = p.getKiller();

            if (killer == null)
                return;

            var inventory = killer.getInventory();
            var mainHand = inventory.getItemInMainHand();
            var meta = mainHand.getItemMeta();

            if (
                    !meta.getDisplayName().equals(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]") ||
                    killer.hasCooldown(Material.IRON_SHOVEL) ||
                    killer.hasPotionEffect(PotionEffectType.UNLUCK) ||
                    !p.getPassengers().isEmpty()
            ) {
                p.getKiller().playSound(p.getKiller(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 2);
                if (p.hasPotionEffect(PotionEffectType.GLOWING)) {
                    p.getKiller().sendMessage(ChatColor.GREEN + "You gained a little bit of money for killing a criminal.");
                    Keys.MONEY.set(p.getKiller(), Keys.MONEY.get(p.getKiller(), 0.0) + 100.0);
                } else {
                    if (PrisonGame.roles.get(p.getKiller()) == Role.PRISONER) {
                        p.getKiller().addPotionEffect(PotionEffectType.GLOWING.createEffect(20 * 5, 0));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        var player = event.getEntity();
        var killer = player.getKiller();

        for (var passenger : player.getPassengers()) {
            if (!(passenger instanceof Player playerPassenger))
                continue;

            playerPassenger.removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
        }

        if (killer != null) {
            if (!killer.equals(player) ) {
                var inventory = killer.getInventory();
                var mainHand = inventory.getItemInMainHand();
                var meta = mainHand.getItemMeta();
                if (
                        meta != null &&
                        meta.getDisplayName().equals(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]") &&
                        !killer.hasCooldown(Material.IRON_SHOVEL) &&
                        !killer.hasPotionEffect(PotionEffectType.UNLUCK) &&
                        player.getPassengers().isEmpty()
                ) {
                    event.setCancelled(true);
                    event.getEntity().addPotionEffect(PotionEffectType.WEAKNESS.createEffect(20 * 30, 255));
                    event.getEntity().addPotionEffect(PotionEffectType.DOLPHINS_GRACE.createEffect(20 * 30, 255));
                    event.getEntity().sendTitle(ChatColor.RED + "HANDCUFFED!", "", 20, 160, 20);
                    Player p = event.getEntity();
                    p.getKiller().addPassenger(p);
                    event.getEntity().getKiller().sendMessage(ChatColor.GREEN + "Shift to drop players.");
                    return;
                }
            }
            PrisonGame.killior.put(event.getEntity(), event.getEntity().getKiller());
        }
        event.getDrops().removeIf(i ->
                i.getType() == Material.TRIPWIRE_HOOK ||
                i.getType() == Material.WOODEN_AXE ||
                i.getType() == Material.WOODEN_SWORD ||
                i.getType() == Material.CARROT_ON_A_STICK ||
                i.getType() == Material.IRON_DOOR ||
                i.getType() == Material.STONE_BUTTON ||
                i.getType() == Material.BOWL ||
                i.getType() == Material.GLASS_BOTTLE ||
                i.getType() == Material.IRON_SHOVEL ||
                i.getType() == Material.BUCKET ||
                (i.getItemMeta() != null && i.getItemMeta().getDisplayName().contains("Prisoner Uniform")));
        if (Bukkit.getWorld("world").getTime() > 16000 && Bukkit.getWorld("world").getTime() < 24000) {
            if (event.getPlayer().getKiller() != null) {
                if (event.getPlayer().getKiller().getInventory().getItemInMainHand().getType().equals(Material.TORCH)) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getKiller().getName() + " only prison:lightsin");
                }
            }
        }
        if (PrisonGame.roles.get(event.getEntity()) == Role.SWAT) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getName() + " only prison:invincible");
        }
        if (PrisonGame.roles.get(event.getEntity()) != Role.PRISONER) {
            if (event.getEntity().getKiller() != null) {

                if (event.getEntity().getKiller().getInventory().getItemInMainHand().getType().equals(Material.WOODEN_AXE)) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getKiller().getName() + " only prison:killstaff");
                    PrisonGame.axekills.put(event.getEntity().getKiller(), PrisonGame.axekills.get(event.getEntity().getKiller()) + 1);
                    PrisonGame.worryachieve.put(event.getEntity().getKiller(), 0);
                    if (PrisonGame.axekills.get(event.getEntity().getKiller()) == 5) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getKiller().getName() + " only prison:oneman");
                    }
                }
            }
        }
        if (PrisonGame.roles.get(event.getEntity()) == Role.PRISONER) {
            if (event.getEntity().getKiller() != null) {
                if (PrisonGame.roles.get(event.getEntity().getKiller()) == Role.WARDEN) {
                    if (event.getEntity().getKiller().getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getKiller().getName() + " only prison:yoink");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getName() + " only prison:skillissue");
                    }
                }
            }
        }
        if (PrisonGame.warden != null) {
            if (PrisonGame.warden.equals(event.getEntity())) {
                if (event.getEntity().getKiller() != null) {
                    if (PrisonGame.roles.get(event.getEntity().getKiller()) != Role.PRISONER) {
                        if (PrisonGame.roles.get(event.getEntity().getKiller()) == Role.NURSE) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getKiller().getName() + " only prison:incorrectmogus");
                        }
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getName() + " only prison:dieguard");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getKiller().getName() + " only prison:killwardenguard");
                        if (PrisonGame.active.getName().equals("Skeld")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getKiller().getName() + " only prison:impostor");
                        }
                    }
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getKiller().getName() + " only prison:killwarden");
                }
                if (PrisonGame.wardentime.get(event.getEntity()) < 0) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getName() + " only prison:afinishedstory");
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + p.getName() + " only prison:pbb");
                    }
                }
                PrisonGame.wardenCooldown = 40;
                event.getDrops().clear();
                PrisonGame.warden = null;
                PrisonGame.roles.put(event.getEntity(), Role.PRISONER);
                MyListener.playerJoin(event.getEntity(), false);
            }
        }
        if (PrisonGame.roles.get(event.getEntity()) == Role.PRISONER) {
            event.setDeathMessage(ChatColor.GRAY + event.getDeathMessage());
            if (event.getEntity().getKiller() != null) {
                if (PrisonGame.roles.get(event.getEntity().getKiller()) == Role.WARDEN && event.getEntity().hasPotionEffect(PotionEffectType.UNLUCK)) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getName() + " only prison:badluck");
                }
                if (PrisonGame.escaped.get(event.getEntity().getKiller())) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getEntity().getKiller().getName() + " only prison:nmng");
                }
            }
        }
        if (PrisonGame.roles.get(event.getEntity()) != Role.PRISONER) {
            event.setDeathMessage(ChatColor.GOLD + event.getDeathMessage());
        }
    }
}
