package prisongame.prisongame.commands.danger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import prisongame.prisongame.lib.Keys;

public class ResetAscensionCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            p.getPersistentDataContainer().remove(Keys.RANDOM_ITEMS.key());
            p.getPersistentDataContainer().remove(Keys.TAX_EVASION.key());
            p.getPersistentDataContainer().remove(Keys.SPAWN_PROTECTION.key());
            p.getPersistentDataContainer().remove(Keys.REINFORCEMENT.key());
            p.getPersistentDataContainer().remove(Keys.SEMICLOAK.key());
            p.getPersistentDataContainer().remove(Keys.ASCENSION_COINS.key());
            p.sendMessage(ChatColor.RED + "Your ascension has been reset!");
        }
        return true;
    }
}