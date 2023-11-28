package prisongame.prisongame.commands.staff;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import prisongame.prisongame.PrisonGame;

public class BuilderCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player) || !player.hasPermission("minecraft.command.gamemode")) {
            sender.sendMessage("You aren't a builder!");
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("toggle")) {
            var enabled = !PrisonGame.builder.getOrDefault(player, false);
            PrisonGame.builder.put(player, enabled);
            player.sendMessage(PrisonGame.mm.deserialize(
                    "<gray><verb> builder mode.",
                    Placeholder.component("verb", Component.text(enabled ? "Enabled" : "Disabled"))
            ));
            return true;
        }

        Inventory inv = Bukkit.createInventory(null, 9 * 6, "BUILDER ZONE");
        inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "Fortress [CMD]", "tp 0 0 0"));
        inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "HyperTech [CMD]", "tp 0 0 -1000"));
        inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "The End [CMD]", "mvtp endprison", ChatColor.RED + "DO /mvtp world TO GET OUT!"));
        inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "Train [CMD]", "tp 0 0 1000"));
        inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "Gladiator [CMD]", "tp -2000 0 2000"));
        inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "Island [CMD]", "tp 2000 0 -2000"));
        inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "Santa's Workshop [CMD]", "tp 2000 0 2000"));
        inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "Volcano [CMD]", "tp -2000 0 -2000"));
        inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "Skeld [CMD]", "tp 1500 0 1500"));
        inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "RAG [CMD]", "tp 200 0 200"));
        inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "Nether [CMD]", "tp 1000 0 1000"));
        inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "Boat [CMD]", "tp -1000 0 0"));
        inv.addItem(PrisonGame.createGuiItem(Material.ENDER_PEARL, ChatColor.YELLOW + "Maximum Security [CMD]", "tp 700 0 700"));
        inv.addItem(PrisonGame.createGuiItem(Material.PLAYER_HEAD, ChatColor.YELLOW + "Teleport All Creative [CMD]", "tp @a[gamemode=creative] @s"));
        inv.addItem(PrisonGame.createGuiItem(Material.PLAYER_HEAD, ChatColor.YELLOW + "Get Coords [CUSTOM]", "gets you the coords required for me to put in the plugin"));

        player.openInventory(inv);
        return true;
    }
}

