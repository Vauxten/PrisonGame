package prisongame.prisongame.listeners;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffectType;
import prisongame.prisongame.MyListener;
import prisongame.prisongame.Prison;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.Config;
import prisongame.prisongame.lib.Keys;
import prisongame.prisongame.lib.Role;

import static prisongame.prisongame.MyListener.reloadBert;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getInventory().getType().equals(InventoryType.PLAYER)) {
            if (event.getCurrentItem() != null) {
                if (event.getCurrentItem().getItemMeta() != null) {
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "2x Income")) {
                        if (Keys.ASCENSION_COINS.get(event.getWhoClicked()) >= 25) {
                            Keys.DOUBLE_INCOME.set(event.getWhoClicked(), 1);
                            Keys.ASCENSION_COINS.set(event.getWhoClicked(), Keys.ASCENSION_COINS.get(event.getWhoClicked()) - 25);
                            event.getWhoClicked().closeInventory();
                        }
                        event.setCancelled(true);
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "Tax Evasion")) {
                        if (Keys.ASCENSION_COINS.get(event.getWhoClicked()) >= 25) {
                            Keys.TAX_EVASION.set(event.getWhoClicked(), 1);
                            Keys.ASCENSION_COINS.set(event.getWhoClicked(), Keys.ASCENSION_COINS.get(event.getWhoClicked()) - 25);
                            event.setCancelled(true);
                            event.getWhoClicked().closeInventory();
                        }
                        event.setCancelled(true);
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "Semi Cloak")) {
                        if (Keys.ASCENSION_COINS.get(event.getWhoClicked()) >= 5) {
                            Keys.SEMICLOAK.set(event.getWhoClicked(), 1);
                            Keys.ASCENSION_COINS.set(event.getWhoClicked(), Keys.ASCENSION_COINS.get(event.getWhoClicked()) - 5);
                            event.setCancelled(true);
                            event.getWhoClicked().closeInventory();
                        }
                        event.setCancelled(true);
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "Reinforcements")) {
                        if (Keys.ASCENSION_COINS.get(event.getWhoClicked()) >= 30) {
                            Keys.REINFORCEMENT.set(event.getWhoClicked(), 1);
                            Keys.ASCENSION_COINS.set(event.getWhoClicked(), Keys.ASCENSION_COINS.get(event.getWhoClicked()) - 30);
                            event.setCancelled(true);
                            event.getWhoClicked().closeInventory();
                        }
                        event.setCancelled(true);
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "ProtSpawn")) {
                        if (Keys.ASCENSION_COINS.get(event.getWhoClicked()) >= 10) {
                            Keys.SPAWN_PROTECTION.set(event.getWhoClicked(), 1);
                            Keys.ASCENSION_COINS.set(event.getWhoClicked(), Keys.ASCENSION_COINS.get(event.getWhoClicked()) - 10);
                            event.setCancelled(true);
                            event.getWhoClicked().closeInventory();
                        }
                        event.setCancelled(true);
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_AQUA + "Random Items")) {
                        if (Keys.ASCENSION_COINS.get(event.getWhoClicked()) >= 3) {
                            Keys.RANDOM_ITEMS.set(event.getWhoClicked(), 1);
                            Keys.ASCENSION_COINS.set(event.getWhoClicked(), Keys.ASCENSION_COINS.get(event.getWhoClicked()) - 3);
                            event.setCancelled(true);
                            event.getWhoClicked().closeInventory();
                        }
                        event.setCancelled(true);
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.YELLOW + "Get Coords [CUSTOM]")) {
                        event.setCancelled(true);
                        event.getWhoClicked().sendMessage("nl(\"" + event.getWhoClicked().getWorld().getName() + "\", " + event.getWhoClicked().getLocation().getX() + "D," + event.getWhoClicked().getLocation().getY() + "D," + event.getWhoClicked().getLocation().getZ() + "D," + event.getWhoClicked().getLocation().getYaw() + "f," + event.getWhoClicked().getLocation().getPitch() + "f)");
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().contains("[CMD]")) {
                        if (event.getWhoClicked().hasPermission("minecraft.command.gamemode")) {
                            if (event.getCurrentItem().getItemMeta().getLore() != null) {
                                event.setCancelled(true);
                                Bukkit.dispatchCommand(event.getWhoClicked(), event.getCurrentItem().getItemMeta().getLore().get(0));
                            }
                        }
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "epic bertude night vision")) {
                        event.setCancelled(true);
                        if (!Keys.NIGHT_VISION.has(event.getWhoClicked())) {
                            Keys.NIGHT_VISION.set(event.getWhoClicked(), 1);
                            event.getWhoClicked().sendMessage("ok i changed that for u lol");
                            Player p = (Player) event.getWhoClicked();
                            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                        } else {
                            Keys.NIGHT_VISION.remove(event.getWhoClicked());
                            event.getWhoClicked().sendMessage("ok i changed that for u lol");
                            Player p = (Player) event.getWhoClicked();
                            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                        }
                    }
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "old tab")) {
                        event.setCancelled(true);
                        if (!Keys.OLD_TAB.has(event.getWhoClicked())) {
                            Keys.OLD_TAB.set(event.getWhoClicked(), 1);
                            event.getWhoClicked().sendMessage("ok i changed that for u lol");
                            Player p = (Player) event.getWhoClicked();
                            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                        } else {
                            Keys.OLD_TAB.remove(event.getWhoClicked());
                            event.getWhoClicked().sendMessage("ok i changed that for u lol");
                            Player p = (Player) event.getWhoClicked();
                            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                        }
                    }

                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "-1 dollar")) {
                        if (!event.getWhoClicked().hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
                            event.setCancelled(true);
                            event.getWhoClicked().damage(999999);
                            Bukkit.broadcastMessage(event.getWhoClicked().getName() + " was robbed by bertrude (L)");
                        } else {
                            event.getWhoClicked().sendMessage("Bertrude was nice today and decided not to rob you :D");
                        }
                    }

                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "no warden spaces")) {
                        event.setCancelled(true);
                        if (!Keys.NO_WARDEN_SPACES.has(event.getWhoClicked())) {
                            Keys.NO_WARDEN_SPACES.set(event.getWhoClicked(), 1);
                            event.getWhoClicked().sendMessage("ok i changed that for u lol");
                            Player p = (Player) event.getWhoClicked();
                            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                        } else {
                            Keys.NO_WARDEN_SPACES.remove(event.getWhoClicked());
                            event.getWhoClicked().sendMessage("ok i changed that for u lol");
                            Player p = (Player) event.getWhoClicked();
                            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1, 1);
                        }
                    }

                }
            }
        }
    }
    

    @EventHandler
    public void onInventoryClick3(InventoryClickEvent event) {
        if (event.getCurrentItem() != null) {
            if (event.getCurrentItem().getType().equals(Material.RED_STAINED_GLASS_PANE) || event.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE)) {
                event.setCancelled(true);
            }
            if (event.getCurrentItem().getItemMeta() != null) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Not Drugs")) {
                    if (Keys.MONEY.get(event.getWhoClicked(), 0.0) >= 30.0) {
                        Keys.MONEY.set(event.getWhoClicked(), Keys.MONEY.get(event.getWhoClicked(), 0.0)  - 30.0);
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                    }
                }

                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Scrap Metal")) {
                    if (Keys.MONEY.get(event.getWhoClicked(), 0.0) >= 150.0) {
                        Keys.MONEY.set(event.getWhoClicked(), Keys.MONEY.get(event.getWhoClicked(), 0.0)  - 150.0);
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.RAW_IRON));
                    }
                }

                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Dagger")) {
                    if (Keys.MONEY.get(event.getWhoClicked(), 0.0) >= 1000.0) {
                        Keys.MONEY.set(event.getWhoClicked(), Keys.MONEY.get(event.getWhoClicked(), 0.0)  - 1000.0);
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.IRON_SWORD));
                    }
                }

                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Chainmail Helmet")) {
                    if (Keys.MONEY.get(event.getWhoClicked(), 0.0) >= 300.0) {
                        Keys.MONEY.set(event.getWhoClicked(), Keys.MONEY.get(event.getWhoClicked(), 0.0)  - 300.0);
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.CHAINMAIL_HELMET));
                    }
                }

                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Soup")) {
                    if (Keys.MONEY.get(event.getWhoClicked(), 0.0) >= 2.0) {
                        Keys.MONEY.set(event.getWhoClicked(), Keys.MONEY.get(event.getWhoClicked(), 0.0)  - 2.0);
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.BEETROOT_SOUP));
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Supreme Stick")) {
                    if (Keys.MONEY.get(event.getWhoClicked(), 0.0) >= 50.0) {
                        Keys.MONEY.set(event.getWhoClicked(), Keys.MONEY.get(event.getWhoClicked(), 0.0)  - 50.0);
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.STICK));
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Coal")) {
                    if (Keys.MONEY.get(event.getWhoClicked(), 0.0) >= 30.0) {
                        Keys.MONEY.set(event.getWhoClicked(), Keys.MONEY.get(event.getWhoClicked(), 0.0)  - 30.0);
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.COAL));
                    }
                }

                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "SWAT Guards")) {
                    if (Keys.MONEY.get(event.getWhoClicked(), 0.0) >= 2500.0) {
                        Keys.MONEY.set(event.getWhoClicked(), Keys.MONEY.get(event.getWhoClicked(), 0.0)  - 2.0);
                        PrisonGame.swat = true;
                        Bukkit.broadcastMessage(ChatColor.GREEN + event.getWhoClicked().getName() + " has enabled SWAT guards!");
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Prot 1")) {
                    if (Keys.MONEY.get(event.getWhoClicked(), 0.0) >= 500.0) {
                        Boolean shouldpay = false;
                        if (event.getWhoClicked().getInventory().getHelmet() != null) {
                            if (event.getWhoClicked().getInventory().getHelmet().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                                event.getWhoClicked().getInventory().getHelmet().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                                shouldpay = true;
                            }
                        }
                        if (event.getWhoClicked().getInventory().getChestplate() != null) {
                            if (event.getWhoClicked().getInventory().getChestplate().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                                event.getWhoClicked().getInventory().getChestplate().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                                shouldpay = true;
                            }
                        }
                        if (event.getWhoClicked().getInventory().getLeggings() != null) {
                            if (event.getWhoClicked().getInventory().getLeggings().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                                event.getWhoClicked().getInventory().getLeggings().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                                shouldpay = true;
                            }
                        }
                        if (event.getWhoClicked().getInventory().getBoots() != null) {
                            if (event.getWhoClicked().getInventory().getBoots().getItemMeta().hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                                event.getWhoClicked().getInventory().getBoots().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                                shouldpay = true;
                            }
                        }
                        if (shouldpay)
                            Keys.MONEY.set(event.getWhoClicked(), Keys.MONEY.get(event.getWhoClicked(), 0.0)  - 30.0);
                    }
                }
                if (PrisonGame.warden != null) {
                    if (PrisonGame.swapcool <= 0 && PrisonGame.warden.equals(event.getWhoClicked())) {
                        var name = event.getCurrentItem().getItemMeta().getDisplayName().replace("§", "&");

                        for (var prison : Config.prisons.values()) {
                            if (prison.displayName.equals(name)) {
                                switchMap(prison);
                                event.setCancelled(true);
                                break;
                            }
                        }
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Rock")) {
                    event.setCancelled(true);
                    if (event.getWhoClicked().getInventory().containsAtLeast(new ItemStack(Material.STONE_BUTTON), 9)) {
                        event.getWhoClicked().getInventory().removeItem(new ItemStack(Material.STONE_BUTTON, 9));
                        event.getWhoClicked().getInventory().addItem(new ItemStack(Material.COBBLESTONE));
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Paper")) {
                    event.setCancelled(true);
                    if (Keys.MONEY.get(event.getWhoClicked(), 0.0) >= 15.0) {
                        if (event.getWhoClicked().getInventory().contains(Material.COAL) && event.getWhoClicked().getInventory().contains(Material.RAW_IRON)) {
                            Keys.MONEY.set(event.getWhoClicked(), Keys.MONEY.get(event.getWhoClicked(), 0.0) - 15.0);
                            event.getWhoClicked().getInventory().removeItem(new ItemStack(Material.COAL, 1));
                            event.getWhoClicked().getInventory().removeItem(new ItemStack(Material.RAW_IRON, 1));
                            event.getWhoClicked().getInventory().addItem(new ItemStack(Material.PAPER));
                        }
                    } else {
                        event.getWhoClicked().sendMessage(ChatColor.RED + "Not enough money!");
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Fake Card")) {
                    event.setCancelled(true);
                    if (event.getWhoClicked().getInventory().containsAtLeast(new ItemStack(Material.PAPER), 3) && event.getWhoClicked().getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                        event.getWhoClicked().getInventory().removeItem(new ItemStack(Material.PAPER, 3));
                        event.getWhoClicked().getInventory().removeItem(new ItemStack(Material.STICK, 2));
                        ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
                        ItemMeta cardm = card.getItemMeta();
                        cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
                        card.setItemMeta(cardm);
                        event.getWhoClicked().getInventory().addItem(card);
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GRAY + "WireCutters")) {
                    event.setCancelled(true);
                    if (event.getWhoClicked().getInventory().containsAtLeast(new ItemStack(Material.COBBLESTONE), 1) &&  event.getWhoClicked().getInventory().containsAtLeast(new ItemStack(Material.RAW_IRON), 4) && event.getWhoClicked().getInventory().containsAtLeast(new ItemStack(Material.STICK), 2)) {
                        event.getWhoClicked().getInventory().removeItem(new ItemStack(Material.RAW_IRON, 4));
                        event.getWhoClicked().getInventory().removeItem(new ItemStack(Material.STICK, 2));
                        event.getWhoClicked().getInventory().removeItem(new ItemStack(Material.COBBLESTONE, 1));
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give " + event.getWhoClicked().getName() + " iron_pickaxe{Damage:245,display:{Name:'[{\"text\":\"WireCutters\",\"italic\":false,\"color\":\"gray\"},{\"text\":\" \"},{\"text\":\"[CONTRABAND]\",\"color\":\"red\"}]'},Enchantments:[{id:efficiency,lvl:5}],HideFlags:1,CanDestroy:[iron_bars]} 1");
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_GRAY + "Cloak")) {
                    event.setCancelled(true);
                    if (Keys.MONEY.get(event.getWhoClicked(), 0.0) >= 15.0) {
                        if (event.getWhoClicked().getInventory().contains(Material.COAL)) {
                            Keys.MONEY.set(event.getWhoClicked(), Keys.MONEY.get(event.getWhoClicked(), 0.0) - 15.0);
                            event.getWhoClicked().getInventory().removeItem(new ItemStack(Material.COAL, 1));
                            ItemStack orangeboot = new ItemStack(Material.LEATHER_CHESTPLATE);

                            LeatherArmorMeta orangelegItemMeta = (LeatherArmorMeta) orangeboot.getItemMeta();
                            orangelegItemMeta.setDisplayName(ChatColor.DARK_GRAY + "Cloak Chestplate");
                            orangelegItemMeta.setColor(Color.BLACK);
                            orangeboot.setItemMeta(orangelegItemMeta);
                            event.getWhoClicked().getInventory().setChestplate(orangeboot);
                        }
                    } else {
                        event.getWhoClicked().sendMessage(ChatColor.RED + "Not enough money!");
                    }
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Normal Crafting")) {
                    event.getWhoClicked().openWorkbench(null, true);
                }
            }
        }
    }

    public static void switchMap(Prison prison) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getDisplayName().contains("ASCENDING") || PrisonGame.builder.getOrDefault(player, false))
                continue;

            player.teleport(prison.spwn);
            if (PrisonGame.roles.get(player) != Role.PRISONER) {
                player.teleport(prison.wardenspawn);
            }
        }

        PrisonGame.BBpower = 100;
        PrisonGame.active = prison;
        PrisonGame.swapcool = 20 * 60 * 5;

        reloadBert();
        Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"),-1023,-57,-994)).setType(Material.REDSTONE_BLOCK);

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (PrisonGame.builder.get(player))
                continue;

            if (player.isSleeping())
                player.wakeup(false);

            if (player.isInsideVehicle()) {
                player.removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
                player.removePotionEffect(PotionEffectType.WEAKNESS);
                player.getVehicle().removePassenger(player);
            }

            for (var passenger : player.getPassengers()) {
                if (passenger instanceof Player playerPassenger) {
                    playerPassenger.removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
                    playerPassenger.removePotionEffect(PotionEffectType.WEAKNESS);
                }

                player.removePassenger(passenger);
            }

            if (PrisonGame.roles.get(player) != Role.WARDEN) {
                MyListener.playerJoin(player, true);
                player.sendTitle(ChatColor.GREEN + "New prison!", ChatColor.BOLD + prison.name.toUpperCase());
                continue;
            }

            player.teleport(prison.getWardenspawn());

            Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                player.teleport(prison.getWardenspawn());
            }, 5);

            if (!player.getDisplayName().contains("ASCENDING"))
                player.sendTitle("New prison!", prison.name.toUpperCase());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClick4(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!player.getOpenInventory().getTitle().equals("Map Switch")) {
            if (player.getGameMode().equals(GameMode.ADVENTURE) || PrisonGame.roles.get(player) != Role.WARDEN || event.isCancelled() || player.getOpenInventory().getType() == InventoryType.CRAFTING) {
                return;
            }
            player.sendMessage(ChatColor.RED + "Wardens cannot interact with containers.");
            event.setCancelled(true);
        }
    }
}
