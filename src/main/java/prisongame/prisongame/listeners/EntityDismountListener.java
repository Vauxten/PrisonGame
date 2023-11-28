package prisongame.prisongame.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;
import org.spigotmc.event.entity.EntityDismountEvent;
import prisongame.prisongame.PrisonGame;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EntityDismountListener implements Listener {
    @EventHandler
    public void onDismountHandcuffs(EntityDismountEvent event) {
        Entity vehicle = event.getDismounted();
        Entity passenger = event.getEntity();

        if (!(passenger instanceof Player && vehicle instanceof Player))
            return;

        Player player = (Player) passenger;

        if (!player.hasPotionEffect(PotionEffectType.DOLPHINS_GRACE))
            return;

        if (isDisconnected(player)) {
            player.removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
            vehicle.removePassenger(player);
            return;
        }

        event.setCancelled(true);
    }

    private boolean isDisconnected(Player player) {
        // player.getHandle().connection.isDisconnected()

        try {
            Method getHandle = player.getClass().getDeclaredMethod("getHandle");
            Object handle = getHandle.invoke(player);
            Field connectionField = handle.getClass().getDeclaredField("b");
            Object connection = connectionField.get(handle);
            Method isDisconnectedMethod = connection.getClass().getMethod("isDisconnected");
            return (boolean) isDisconnectedMethod.invoke(connection);
        } catch (NoSuchFieldException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            return false;
        }
    }
}
