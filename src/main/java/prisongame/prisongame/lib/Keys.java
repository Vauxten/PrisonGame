package prisongame.prisongame.lib;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;
import prisongame.prisongame.PrisonGame;

import java.util.Arrays;

public interface Keys {
    Key<Integer> NIGHT_VISION = new Key<>("NIGHT_VISION", new NamespacedKey(PrisonGame.instance, "night"), KeyTypes.INTEGER);
    Key<Double> MONEY = new Key<>("MONEY", new NamespacedKey(PrisonGame.instance, "money"), KeyTypes.DOUBLE);
    Key<Double> PREVIOUS_MONEY = new Key<>("PREVIOUS_MONEY", new NamespacedKey(PrisonGame.instance, "prevmoney"), KeyTypes.DOUBLE);
    Key<Integer> SEASON = new Key<>("SEASON", new NamespacedKey(PrisonGame.instance, "season"), KeyTypes.INTEGER);
    Key<Integer> NO_WARDEN_SPACES = new Key<>("NO_WARDEN_SPACES", new NamespacedKey(PrisonGame.instance, "whiff"), KeyTypes.INTEGER);
    Key<Integer> MUTED = new Key<>("MUTED", new NamespacedKey(PrisonGame.instance, "mutedd"), KeyTypes.INTEGER);
    Key<Integer> COARSE_MINED = new Key<>("COARSE_MINED", new NamespacedKey(PrisonGame.instance, "coarsemined"), KeyTypes.INTEGER);
    Key<Integer> ASCENSION_COINS = new Key<>("ASCENSION_COINS", new NamespacedKey(PrisonGame.instance, "ascendcoins"), KeyTypes.INTEGER);
    Key<Double> BACKUP_MONEY = new Key<>("BACKUP_MONEY", new NamespacedKey(PrisonGame.instance, "bckupmny"), KeyTypes.DOUBLE);
    Key<Integer> HEAD_GUARD = new Key<>("HEAD_GUARD", new NamespacedKey(PrisonGame.instance, "headguard"), KeyTypes.INTEGER);
    Key<Integer> OLD_TAB = new Key<>("OLD_TAB", new NamespacedKey(PrisonGame.instance, "oldtab"), KeyTypes.INTEGER);
    Key<Integer> DOUBLE_INCOME = new Key<>("DOUBLE_INCOME", new NamespacedKey(PrisonGame.instance, "doubleinc"), KeyTypes.INTEGER);
    Key<Integer> TAX_EVASION = new Key<>("TAX_EVASION", new NamespacedKey(PrisonGame.instance, "taxev"), KeyTypes.INTEGER);
    Key<Integer> SEMICLOAK = new Key<>("SEMICLOAK", new NamespacedKey(PrisonGame.instance, "scloak"), KeyTypes.INTEGER);
    Key<Integer> REINFORCEMENT = new Key<>("REINFORCEMENT", new NamespacedKey(PrisonGame.instance, "reinf"), KeyTypes.INTEGER);
    Key<Integer> SPAWN_PROTECTION = new Key<>("SPAWN_PROTECTION", new NamespacedKey(PrisonGame.instance, "prots"), KeyTypes.INTEGER);
    Key<Integer> RANDOM_ITEMS = new Key<>("RANDOM_ITEMS", new NamespacedKey(PrisonGame.instance, "rand"), KeyTypes.INTEGER);
    Key<String> RANK_PREFIX = new Key<>("RANK_PREFIX", new NamespacedKey(PrisonGame.instance, "rankprefix"), KeyTypes.STRING);
    Key<Double> TRUST = new Key<>("TRUST", new NamespacedKey(PrisonGame.instance, "trust"), KeyTypes.DOUBLE);
    Key<Integer> GUARD_ELO = new Key<>("GUARD_ELO", new NamespacedKey(PrisonGame.instance, "elo"), KeyTypes.INTEGER);

    static @Nullable Key<?> valueOf(String name) {
        try {
            var field = Keys.class.getDeclaredField(name);
            return (Key<?>) field.get(null);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    static Key<?>[] values() {
        return Arrays.stream(Keys.class.getDeclaredFields())
                .filter((f) -> f.getType().equals(Key.class))
                .map((f) -> {
                    try {
                        return (Key<?>) f.get(null);
                    } catch (ReflectiveOperationException exception) {
                        PrisonGame.instance.getLogger().severe(String.format(
                                "An error occurred fetching all keys.\n%s",
                                exception.getMessage()
                        ));
                        return null;
                    }
                })
                .toArray(Key[]::new);
    }
}
