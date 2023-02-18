package prisongame.prisongame.commands.danger;

import me.libraryaddict.disguise.DisguiseAPI;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import prisongame.prisongame.MyListener;
import prisongame.prisongame.PrisonGame;
import prisongame.prisongame.lib.Role;

public class NormalCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && PrisonGame.hardmode.get((Player) sender)) {
            Player pe = (Player) sender;
            if (!((Player) sender).getDisplayName().contains("SOLITARY") && !((Player) sender).hasCooldown(Material.IRON_DOOR) && !new Location(pe.getWorld(), pe.getLocation().getX(), pe.getLocation().getY() - 1, pe.getLocation().getZ()).getBlock().getType().equals(Material.RED_SAND)) {
                Player p = (Player) sender;
                p.getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, p.getPersistentDataContainer().getOrDefault(PrisonGame.bckupmny, PersistentDataType.DOUBLE, 0.0));
                p.getPersistentDataContainer().set(PrisonGame.bckupmny, PersistentDataType.DOUBLE, 0.0);
                if (PrisonGame.warden != null) {
                    if (PrisonGame.warden.equals(sender)) {
                        PrisonGame.warden = null;
                    }
                }
                PrisonGame.roles.put((Player) sender, Role.PRISONER);
                DisguiseAPI.undisguiseToAll(p);
                PrisonGame.hardmode.put(p, false);
                MyListener.playerJoin(p, false);
            }
        } else {
            sender.sendMessage(Color.fromRGB(255, 59, 98) + "Really? Did you deadass try to run normal when already in normal mode? Come on. You're better than this. (I had to add this since people were losing money by doing normal... come on...)");
        }
        return true;
    }
}
