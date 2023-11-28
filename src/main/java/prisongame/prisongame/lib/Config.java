package prisongame.prisongame.lib;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.tomlj.Toml;
import org.tomlj.TomlArray;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;
import prisongame.prisongame.Prison;
import prisongame.prisongame.PrisonGame;

import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {
    private static final PrisonGame INSTANCE = PrisonGame.getPlugin(PrisonGame.class);
    private static final File DATA_FOLDER = INSTANCE.getDataFolder();
    private static final File CONFIG = new File(DATA_FOLDER.getAbsolutePath() + "/config.toml");
    private static final File PRISONS = new File(DATA_FOLDER.getAbsolutePath() + "/prisons.toml");
    private static final World WORLD = Bukkit.getWorld("world");
    private static final Location PLACEHOLDER_LOCATION = new Location(WORLD, 0d, 0d, 0d, 0f, 0f);

    public static Prison defaultPrison;
    public static final Map<String, Prison> prisons = new HashMap<>();

    public static class General {
        public static String discordInvite;
        public static List<String> rules = new ArrayList<>();
    }

    public static class Warden {
        public static class Prefix {
            public static List<String> bannedContainers = new ArrayList<>();
        }

        public static final Map<String, HelpSubcommand> help = new HashMap<>();
    }

    public record HelpSubcommand(List<String> args, String description) {}

    static {
        reload();
    }

    public static void reload() {
        try {
            parseConfig();
            parsePrisons();
        } catch (IOException exception) {
            INSTANCE.getLogger().severe("An error occurred parsing the config.");
            INSTANCE.getLogger().severe(exception.getMessage());
            Bukkit.shutdown();
        }
    }

    private static void parseConfig() throws IOException {
        var config = parseFile(CONFIG);

        Warden.help.clear();

        General.discordInvite = config.getString("general.discord-invite");
        General.rules = arrayToList(config.getArray("general.rules"));

        Warden.Prefix.bannedContainers = arrayToList(config.getArray("warden.prefix.banned-containers"));
        var help = config.getTable("warden.help");

        for (var key : help.keySet()) {
            var table = help.getTable(key);
            var description = table.getString("description");
            List<String> args = arrayToList(table.getArray("args"));

            Warden.help.put(key, new HelpSubcommand(args, description));
        }
    }

    private static <T> List<T> arrayToList(TomlArray array) {
        var list = new ArrayList<T>();

        for (var i = 0; i < array.size(); i++)
            list.add((T) array.get(i));

        return list;
    }

    private static void parsePrisons() throws IOException {
        var prisons = parseFile(PRISONS);

        Config.prisons.clear();

        for (var entry : prisons.getTable("prisons").entrySet()) {
            var key = entry.getKey();
            var prison = (TomlTable) entry.getValue();

            var id = prison.getString("selector-item");
            var material = id.isEmpty() ? Material.AIR : Material.matchMaterial(id);

            Config.prisons.put(key, new Prison(
                    prison.getBoolean("show-in-selector"),
                    material,
                    prison.getString("display-name"),
                    prison.getLong("priority").intValue(),
                    prison.getString("name"),
                    PLACEHOLDER_LOCATION,
                    parseLocation(prisons, key, "track-1"),
                    parseLocation(prisons, key, "track-2"),
                    parseLocation(prisons, key, "nurse-1"),
                    parseLocation(prisons, key, "nurse-2"),
                    parseLocation(prisons, key, "warden"),
                    parseLocation(prisons, key, "prisoner"),
                    parseLocation(prisons, key, "bm-in"),
                    parseLocation(prisons, key, "bm-out"),
                    parseLocation(prisons, key, "solitary"),
                    parseLocation(prisons, key, "bertrude"),
                    PLACEHOLDER_LOCATION,
                    PLACEHOLDER_LOCATION
            ));
        }

        defaultPrison = Config.prisons.get(prisons.getString("default-prison"));
    }

    private static Location parseLocation(TomlParseResult result, String prison, String key) {
        var location = result.getTable("prisons." + prison).getTable(key);

        if (location == null)
            throw new IllegalArgumentException("Invalid prison " + prison + ", missing " + key);

        var requiredKeys = new ArrayList<String>();
        requiredKeys.add("world");
        requiredKeys.add("x");
        requiredKeys.add("y");
        requiredKeys.add("z");

        if (!location.keySet().containsAll(requiredKeys))
            throw new IllegalArgumentException("Invalid keys for prison " + prison + " and key " + key);

        Double yaw = location.getDouble("yaw");
        Double pitch = location.getDouble("pitch");

        if (yaw == null)
            yaw = 0.0;

        if (pitch == null)
            pitch = 0.0;

        return new Location(
                Bukkit.getWorld(location.getString("world")),
                location.getDouble("x"),
                location.getDouble("y"),
                location.getDouble("z"),
                yaw.floatValue(),
                pitch.floatValue()
        );
    }

    private static TomlParseResult parseFile(File file) throws IOException {
        if (!file.exists()) {
            DATA_FOLDER.mkdir();
            INSTANCE.saveResource(file.getName(), true);
        }

        return Toml.parse(file.toPath());
    }

    // Workaround because clinit doesn't run when accessing children classes
    public static void register() {}
}
