package prisongame.prisongame;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import prisongame.prisongame.commands.*;
import prisongame.prisongame.commands.completers.WardenComplete;
import prisongame.prisongame.commands.danger.HardCommand;
import prisongame.prisongame.commands.danger.NormalCommand;
import prisongame.prisongame.commands.danger.ResetAscensionCommand;
import prisongame.prisongame.commands.economy.PayCommand;
import prisongame.prisongame.commands.economy.staff.NerdCheatCommand;
import prisongame.prisongame.commands.economy.staff.ResetMoneyCommand;
import prisongame.prisongame.commands.economy.staff.SetMoneyCommand;
import prisongame.prisongame.commands.staff.BuilderCommand;
import prisongame.prisongame.lib.HidePlayerList;
import prisongame.prisongame.lib.Role;
import prisongame.prisongame.listeners.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public final class PrisonGame extends JavaPlugin {

    public static HashMap<Player, Double> st = new HashMap<>();
    public static HashMap<Player, Double> sp = new HashMap<>();
    public static Player warden = null;
    public static HashMap<Player, Boolean> escaped = new HashMap<>();
    public static HashMap<Player, Role> roles = new HashMap<>();
    public static HashMap<Player, Integer> askType = new HashMap<>();
    static HashMap<Player, Integer> lastward = new HashMap<>();
    static HashMap<Player, Integer> lastward2 = new HashMap<>();
    static HashMap<Player, Integer> wardenban = new HashMap<>();
    public static HashMap<Player, String> word = new HashMap<>();
    static HashMap<Player, Integer> saidcycle = new HashMap<>();
    public static HashMap<Player, String> prisonnumber = new HashMap<>();
    static HashMap<Player, Double> wealthcycle = new HashMap<>();
    public static HashMap<Player, Integer> wardentime = new HashMap<>();
    static HashMap<Player, Integer> calls = new HashMap<>();
    public static HashMap<Player, Integer> worryachieve = new HashMap<>();
    public static HashMap<Player, Integer> axekills = new HashMap<>();
    static HashMap<Player, Integer> timebet = new HashMap<>();
    public static Boolean givepig = false;
    public static Prison gaeae;
    public static Prison hyper;
    public static Prison endmap;
    public static Prison train;
    public static Prison gladiator;
    public static Prison island;
    public static Prison santa;
    public static Prison volcano;
    public static Prison boat;
    public static Prison nether;
    public static Prison amongus;
    public static Prison ms;
    public static Integer solitcooldown = 0;
    public static Prison active = null;
    public static NamespacedKey nightvis;
    static NamespacedKey rank;
    static NamespacedKey coarsemined;
    public static NamespacedKey hg;
    public static NamespacedKey ascendcoins;
    public static NamespacedKey guardelo;
    public static NamespacedKey doubincome;
    public static NamespacedKey taxevasion;
    static NamespacedKey rankprefix;
    public static NamespacedKey semicloak;
    public static NamespacedKey reinforcement;
    public static NamespacedKey bckupmny;
    public static NamespacedKey protspawn;
    public static NamespacedKey tab;
    public static NamespacedKey randomz;
    public static Integer swapcool = 0;
    public static Integer wardenCooldown = 20;
    public static Integer lockdowncool = 0;
    public static NamespacedKey whiff;
    public static Boolean wardenenabled = false;

    public static NamespacedKey mny;
    static HashMap<Player, Integer> respect = new HashMap<>();
    public static HashMap<Player, Integer> solittime = new HashMap<>();
    static HashMap<Material, Double> moneyore = new HashMap<>();
    static HashMap<Player, Player> handcuff = new HashMap<>();
    public static HashMap<Player, Integer> trustlevel = new HashMap<>();
    public static HashMap<Player, Integer> prisonerlevel = new HashMap<>();
    public static HashMap<Player, Boolean> gotcafefood = new HashMap<>();
    static NamespacedKey trust;
    public static HashMap<Player, Boolean> hardmode = new HashMap<>();
    public static HashMap<Player, Player> killior = new HashMap<>();
    public static NamespacedKey muted;

    public static HashMap<UUID, HashMap<UUID, Integer>> savedPlayerGuards = new HashMap<>();

    public static Material[] oretypes = {
            Material.DEEPSLATE_COPPER_ORE,
            Material.DEEPSLATE_COPPER_ORE,
            Material.DEEPSLATE_COPPER_ORE,
            Material.DEEPSLATE_COPPER_ORE,
            Material.DEEPSLATE_GOLD_ORE,
            Material.DEEPSLATE_GOLD_ORE,
            Material.DEEPSLATE_GOLD_ORE,
            Material.DEEPSLATE_LAPIS_ORE,
            Material.DEEPSLATE_LAPIS_ORE,
            Material.DEEPSLATE_EMERALD_ORE
    };
    public static LivingEntity bertrude;
    static LivingEntity guardsh;

    static Villager bmsh1;
    static Villager bmsh2;
    static Villager shop;
    public static Boolean chatmuted = false;
    public static Boolean grammar = false;
    public static Boolean swat = false;

    public static void tptoBed(Player p) {
        p.teleport(PrisonGame.active.getNursebedOutTP());
    }
    public static Location move(Location loc, Vector offset) {
        // Convert rotation to radians
        float ryaw = -loc.getYaw() / 180f * (float) Math.PI;
        float rpitch = loc.getPitch() / 180f * (float) Math.PI;

        //Conversions found by (a lot of) testing
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        z -= offset.getX() * Math.sin(ryaw);
        z += offset.getY() * Math.cos(ryaw) * Math.sin(rpitch);
        z += offset.getZ() * Math.cos(ryaw) * Math.cos(rpitch);
        x += offset.getX() * Math.cos(ryaw);
        x += offset.getY() * Math.sin(rpitch) * Math.sin(ryaw);
        x += offset.getZ() * Math.sin(ryaw) * Math.cos(rpitch);
        y += offset.getY() * Math.cos(rpitch);
        y -= offset.getZ() * Math.sin(rpitch);
        return new Location(loc.getWorld(), x, y, z, loc.getYaw(), 32);
    }
    public static Location lookAt(Location loc, Location lookat) {
        //Clone the loc to prevent applied changes to the input loc
        loc = loc.clone();

        // Values of change in distance (make it relative)
        double dx = lookat.getX() - loc.getX();
        double dy = lookat.getY() - loc.getY();
        double dz = lookat.getZ() - loc.getZ();

        // Set yaw
        if (dx != 0) {
            // Set yaw start value based on dx
            if (dx < 0) {
                loc.setYaw((float) (1.5 * Math.PI));
            } else {
                loc.setYaw((float) (0.5 * Math.PI));
            }
            loc.setYaw((float) loc.getYaw() - (float) Math.atan(dz / dx));
        } else if (dz < 0) {
            loc.setYaw((float) Math.PI);
        }

        // Get the distance from dx/dz
        double dxz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2));

        // Set pitch
        loc.setPitch((float) -Math.atan(dy / dxz));

        // Set values, convert to degrees (invert the yaw since Bukkit uses a different yaw dimension format)
        loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
        loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);

        return loc;
    }
    @Override
    public void onEnable() {
        // Plugin startup logic

        if (Data.loadData("saveguard.data") != null) {
            Data data = new Data(Data.loadData("saveguard.data"));
            savedPlayerGuards = data.playerguards;
            Bukkit.broadcastMessage("LOADED PLAYER'S GUARDS");
        }
        active = gaeae;
        nightvis = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "night");
        mny = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "money");
        whiff = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "whiff");
        muted = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "mutedd");
        coarsemined = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "coarsemined");
        ascendcoins = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "ascendcoins");
        bckupmny = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "bckupmny");
        hg = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "headguard");
        tab = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "oldtab");

        doubincome = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "doubleinc");
        taxevasion = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "taxev");
        semicloak = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "scloak");
        reinforcement = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "reinf");
        protspawn = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "prots");
        randomz = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "rand");

        rankprefix = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "rankprefix");

        trust = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "trust");
        guardelo = new NamespacedKey(PrisonGame.getPlugin(PrisonGame.class), "elo");

        moneyore.put(Material.DEEPSLATE_COPPER_ORE, 7.5);
        moneyore.put(Material.DEEPSLATE_EMERALD_ORE, 45.0);
        moneyore.put(Material.DEEPSLATE_GOLD_ORE, 25.0);
        moneyore.put(Material.DEEPSLATE_LAPIS_ORE, 15.0);
        moneyore.put(Material.DEEPSLATE_REDSTONE_ORE, 10.0);

        Bukkit.broadcastMessage("RELOAD: Loaded NameSpacedKeys");
        this.getCommand("warden").setExecutor(new WardenCommand());
        this.getCommand("warden").setTabCompleter(new WardenComplete());

        this.getCommand("resign").setExecutor(new ResignCommand());
        this.getCommand("tc").setExecutor(new TeamChatCommand());
        this.getCommand("disc").setExecutor(new DiscCommand());
        this.getCommand("accept").setExecutor(new AcceptCommand());
        this.getCommand("hello").setExecutor(new HelloCommand());
        this.getCommand("nerdcheatcommand").setExecutor(new NerdCheatCommand());
        this.getCommand("rstmoney").setExecutor(new ResetMoneyCommand());
        this.getCommand("pay").setExecutor(new PayCommand());
        this.getCommand("builder").setExecutor(new BuilderCommand());
        this.getCommand("rstascen").setExecutor(new ResetAscensionCommand());
        this.getCommand("hard").setExecutor(new HardCommand());
        this.getCommand("normal").setExecutor(new NormalCommand());
        this.getCommand("setmoney").setExecutor(new SetMoneyCommand());
        this.getCommand("pbsettings").setExecutor(new PBSettingsCommand());

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("RELOAD: Loaded Commands");
            p.setGameMode(GameMode.ADVENTURE);
            if (p.getDisplayName().contains("GUARD")) {
                PrisonGame.roles.put(p, Role.GUARD);
                p.sendMessage("RELOAD: Restored Guards");
            }
            if (p.getDisplayName().contains("NURSE")) {
                PrisonGame.roles.put(p, Role.NURSE);
                p.sendMessage("RELOAD: Restored Nurses");
            }
            if (p.getDisplayName().contains("SWAT")) {
                PrisonGame.roles.put(p, Role.SWAT);
                p.sendMessage("RELOAD: Restored SWATs");
            }
            if (p.getDisplayName().contains("CRIMINAL")) {
                PrisonGame.escaped.put(p, true);
                p.addPotionEffect(PotionEffectType.GLOWING.createEffect(99999, 255));
                p.sendMessage("RELOAD: Restored Criminals");
            }
            if (p.getDisplayName().contains("PRISONER")) {
                PrisonGame.roles.put(p, Role.PRISONER);
                p.sendMessage("RELOAD: Restored Prisoner");
            }
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist on");
        NamespacedKey key = new NamespacedKey(this, "cobble");

        ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(Material.COBBLESTONE));
        recipe.addIngredient(Material.STONE_BUTTON);
        recipe.addIngredient(Material.STONE_BUTTON);
        recipe.addIngredient(Material.STONE_BUTTON);
        recipe.addIngredient(Material.STONE_BUTTON);
        recipe.addIngredient(Material.STONE_BUTTON);
        recipe.addIngredient(Material.STONE_BUTTON);
        recipe.addIngredient(Material.STONE_BUTTON);
        recipe.addIngredient(Material.STONE_BUTTON);
        recipe.addIngredient(Material.STONE_BUTTON);

        Bukkit.broadcastMessage("RELOAD: Loaded Recipes");
        Bukkit.broadcastMessage("RELOAD: Safewaiting For Worlds");

        Bukkit.addRecipe(recipe);
        while (Bukkit.getWorld("world") == null) {
            // hi chat
        }
        // code
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:kick @a Reload");

        for (Entity e : Bukkit.getWorld("world").getEntities()) {
            if (e.getType().equals(EntityType.VILLAGER) || e.getType().equals(EntityType.WOLF)) {
                e.remove();
            }
        }
        Bukkit.broadcastMessage("RELOAD: Removed Entities");
        gaeae = new Prison("Gaeae Fort", new Location(Bukkit.getWorld("world"), 61, -54, -159), new Location(Bukkit.getWorld("world"), 76, -59, -169), new Location(Bukkit.getWorld("world"), 44, -58, -141), new Location(Bukkit.getWorld("world"), 44, -58, -137), new Location(Bukkit.getWorld("world"), 41.5, -52, -120.5), new Location(Bukkit.getWorld("world"), 12, -60, -119), new Location(Bukkit.getWorld("world"), -26.5, -56.5, -115.5), new Location(Bukkit.getWorld("world"), -8.5, -57, -108.5), new Location(Bukkit.getWorld("world"), 33, -59, -132), new Location(Bukkit.getWorld("world"), 70.5, -59, -137.5), new Location(Bukkit.getWorld("world"), 87, -59, -129), new Location(Bukkit.getWorld("world"), 87, -56, -125));
        hyper = new Prison("HyperTech", new Location(Bukkit.getWorld("world"), 18, -56, -988), new Location(Bukkit.getWorld("world"), 8, -59, -981), new Location(Bukkit.getWorld("world"), -29, -58, -988, 0, 0), new Location(Bukkit.getWorld("world"), -29, -58, -991), new Location(Bukkit.getWorld("world"), 12, -53, -970), new Location(Bukkit.getWorld("world"), -18, -59, -995), new Location(Bukkit.getWorld("world"), -35.5, -56.5, -959.5), new Location(Bukkit.getWorld("world"), 3.5, -59, -1006.5), new Location(Bukkit.getWorld("world"), 13, -59, -1009), new Location(Bukkit.getWorld("world"), 0.5, -59, -996.5), new Location(Bukkit.getWorld("world"), 1, -58, -1008), new Location(Bukkit.getWorld("world"), 3, -58, -1008));
        //endmap = new Prison("The End?", new Location(Bukkit.getWorld("endprison"), 7, 133, 8), new Location(Bukkit.getWorld("endprison"), 19, 127, 20), new Location(Bukkit.getWorld("endprison"), -30, 170, -48), new Location(Bukkit.getWorld("endprison"), -32, 170, -47), new Location(Bukkit.getWorld("endprison"), -1, 150, 13), new Location(Bukkit.getWorld("endprison"), 0, 135, -41), new Location(Bukkit.getWorld("world"), -26.5, -56.5, -115.5), new Location(Bukkit.getWorld("endprison"), 0, 131, -6), new Location(Bukkit.getWorld("endprison"), 0, 125, 0), new Location(Bukkit.getWorld("endprison"), 16.5, 129, 2.5), new Location(Bukkit.getWorld("endprison"), -100000, 256, -100000), new Location(Bukkit.getWorld("endprison"), -100000, 256, -100000));
        train = new Prison("Train", nl("world", 0D, 0D, 0D, 0f, 0f), nl("world", 0D, 0D, 0D, 0f, 0f), nl("world", 82.8D, -58D, 953D, 0f, 0f), nl("world", 82.8D, -58D, 953D, 0f, 0f), nl("world", 83.0D, -54D, 980D, -180f, 0f), nl("world", 91.9D, -58D, 982D, 0f, 0f), nl("world", 64D, -56D, 981D, -180f, 0f), nl("world", 75.5D, -58D, 964.5D, 0f, 0f), nl("world", 91D, -58D, 946D, 0f, 0f), nl("world", 80.5D, -54D, 961.5D, 0f, 0f), nl("world", 79D, -58D, 976D, 0f, 0f), nl("world", 79D, -56D, 975D, 0f, 0f));
        gladiator = new Prison("Gladiator", new Location(Bukkit.getWorld("world"), -2024, -55, 1919), new Location(Bukkit.getWorld("world"), -1999, -60, 1940), new Location(Bukkit.getWorld("world"), -2039, -59, 1933, 180, 0), new Location(Bukkit.getWorld("world"), -2040, -59, 1933, 180, 0), new Location(Bukkit.getWorld("world"), -2041, -47, 1957), new Location(Bukkit.getWorld("world"), -2037, -60, 1947), new Location(Bukkit.getWorld("world"), -2030, -60, 2015), new Location(Bukkit.getWorld("world"), -2022, -60, 1967), new Location(Bukkit.getWorld("world"), -1973, -60, 1984), new Location(Bukkit.getWorld("world"), -2022.5, -60, 1957.5), new Location(Bukkit.getWorld("world"), -2084, -60, 1973), new Location(Bukkit.getWorld("world"), -2080, -56, 1973));
        island = new Prison("Island", new Location(Bukkit.getWorld("world"), 1976, -55, -2001), new Location(Bukkit.getWorld("world"), 1968, -60, -2009), new Location(Bukkit.getWorld("world"), 2003, -59, -1988, 90, 0), new Location(Bukkit.getWorld("world"), 2003, -59, -1988, 90, 0), new Location(Bukkit.getWorld("world"), 1988, -60, -1978), new Location(Bukkit.getWorld("world"), 1964, -60, -1981), new Location(Bukkit.getWorld("world"), 1990, -59, -1861), new Location(Bukkit.getWorld("world"), 1989, -60, -1990), new Location(Bukkit.getWorld("world"), 1979, -60, -1982), new Location(Bukkit.getWorld("world"), 1981.5, -60, -1989.5), new Location(Bukkit.getWorld("world"), 1958, -60, -1999), new Location(Bukkit.getWorld("world"), 1962, -57, -1999));
        santa = new Prison("Santa's Workshop", nl("world", 1960D, -56D, 1990D, 0f, 0f), nl("world", 1973D, -60D, 1981D, 0f, 0f), nl("world", 1981D, -59D, 1993D, 0f, 0f), nl("world", 1981D, -59D, 1993D, 0f, 0f), nl("world", 1966D, -53D, 2003D, 0f, 0f), nl("world", 1961D, -60D, 1921D, 0f, 0f), nl("world", 1970D, -59D, 2041D, 0f, 0f), nl("world", 1957D, -60D, 1992D, 0f, 0f), nl("world", 1967D, -53D, 1999D, 0f, 0f), nl("world", 1957.5D, -60D, 2007.5D, 0f, 0f), nl("world", 1989D, -60D, 2008D, 0f, 0f), nl("world", 1989D, -57D, 2013D, 0f, 0f));
        volcano = new Prison("Volcano", nl("world", -2016D, -56D, -1933D, 0f, 0f), nl("world", -2025D, -60D, -1925D, 0f, 0f), nl("world", -2029D, -59D, -2001D, 0f, 0f), nl("world", -2029D, -59D, -2001D, 0f, 0f), nl("world", -2026D, -55D, -1956D, -90f, 0f), nl("world", -2004D, -60D, -1981D, 0f, 0f), nl("world", -1931D, -57D, -1976D, 0f, 0f), nl("world", -2019D, -60D, -1990D, 0f, 0f), nl("world", -2032D, -60D, -1966D, 0f, 0f), nl("world", -2011.5D, -60D, -1965.5D, 0f, 0f), nl("world", -2041D, -60D, -1974D, 0f, 0f), nl("world", -2041D, -57D, -1979D, 0f, 0f));
        boat = new Prison("Boat", nl("world", -1000D, -47D, 24D, 0f, 0f), nl("world", -996D, -50D, 22D, 0f, 0f), nl("world", -998D, -48D, 17D, 0f, 0f), nl("world", -998D, -48D, 17D, 0f, 0f), nl("world", -994D, -44D, 27D, -90f, 0f), nl("world", -993D, -54D, 7D, 0f, 0f), nl("world", -961D, -59D, 64D, 0f, 0f), nl("world", -999.5D, -49D, 7D, 0f, 0f), nl("world", -988D, -57D, 18D, 0f, 0f), nl("world", -987D, -49D, 19D, 0f, 0f), nl("world", -992D, -48D, 27D, 0f, 0f), nl("world", -991D, -49D, 27D, 0f, 0f));
        nether = new Prison("Nether", nl("world", -1000D, -47D, 24D, 0f, 0f), nl("world", -996D, -50D, 22D, 0f, 0f), nl("world", 1009D, -38D, 990D, 0f, 0f), nl("world", 1009D, -38D, 990D, 0f, 0f), nl("world", 1007D, -34D, 994D, -90f, 0f), nl("world", 948D, -39D, 946D, 0f, 0f), nl("world", 937D, -59D, 1030D, 0f, 0f), nl("world", 981D, -35D, 976D, 0f, 0f), nl("world", 1003D, -38D, 1003D, 0f, 0f), nl("world", 956.3, -38D, -168.8, 0f, 0f), nl("world", -992D, -48D, 27D, 0f, 0f), nl("world", -991D, -49D, 27D, 0f, 0f));
        amongus = new Prison(
                "Skeld",
                nl("world", 0D, 0D, 0D, 0F, 0f),
                nl("world", 0D, 0D, 0D, 0F, 0f),
                nl("world", 1484D, -33D, 1475D, 0F, 0f),
                nl("world", 1484D, -33D, 1475D, 0F, 0f),
                nl("world", 1477D, -34D, 1433D, 0F, 0f),
                nl("world", 1500D, -40D, 1494D, 0F, 0f),
                nl("world", 1445D, -34D, 1497D, 0F, 0f),
                nl("world", 1488D, -34D, 1507D, 0F, 0f),
                nl("world", 1472D, -33D, 1470D, 0F, 0f),
                nl("world", 1483D, -34D, 1502D, 0F, 0f),
                nl("world", 0D, 0D, 0D, 0F, 0f),
                nl("world", 0D, 0D, 0D, 0F, 0f)
        );
        ms = new Prison(
                "Maximum Security",
                nl("world", 0D, 0D, 0D, 0F, 0f),
                nl("world", 0D, 0D, 0D, 0F, 0f),
                nl("world", 707.5, -58D, 705.5, 0F, 0f),
                nl("world", 707.5, -58D, 705.5, 0F, 0f),
                nl("world", 699.5, -55D, 689.5, 0F, 0f),
                nl("world", 689.5, -59D, 690.5, 0F, 0f),
                nl("world", 673.5, -60D, 664.5, 0F, 0f),
                nl("world", 689.5, -59D, 697.5, 0F, 0f),
                nl("world", 691.5, -59D, 702.5, 0F, 0f),
                nl("world", 705.5, -59D, 692.5, 0F, 0f),
                nl("world", 0D, 0D, 0D, 0F, 0f),
                nl("world", 0D, 0D, 0D, 0F, 0f)
        );
        active = gaeae;
        Bukkit.broadcastMessage("RELOAD: Loaded Maps");
        MyListener.reloadBert();
        Bukkit.broadcastMessage("RELOAD: loaded bertrude lmao");

        wardenenabled = true;
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.removePotionEffect(PotionEffectType.DARKNESS);
            p.removePotionEffect(PotionEffectType.WEAKNESS);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist off");
            p.sendTitle(ChatColor.GREEN + "Loaded!", "thanks for your patience!", 0, 40, 0);
            PrisonGame.st.put(p, 0.0);
            PrisonGame.sp.put(p, 0.0);
            if (!PrisonGame.roles.containsKey(p)) {
                PrisonGame.roles.put(p, Role.PRISONER);
                MyListener.playerJoin(p, true);
            }

            if (PrisonGame.warden != null) {
                PrisonGame.warden.teleport(active.wardenspawn);
            }
        }
        MyTask task = new MyTask();
        task.runTaskTimer(getPlugin(this.getClass()), 0, 1);

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new AsyncPlayerChatListener(), this);
        pm.registerEvents(new BlockBreakListener(), this);
        pm.registerEvents(new BlockPlaceListener(), this);
        pm.registerEvents(new EntityDamageByEntityListener(), this);
        pm.registerEvents(new EntityDamageListener(), this);
        pm.registerEvents(new EntityDismountListener(), this);
        pm.registerEvents(new EntityMoveListener(), this);
        pm.registerEvents(new InventoryClickListener(), this);
        pm.registerEvents(new PlayerAdvancementDoneListener(), this);
        pm.registerEvents(new PlayerBedLeaveListener(), this);
        pm.registerEvents(new PlayerChatListener(), this);
        pm.registerEvents(new PlayerDeathListener(), this);
        pm.registerEvents(new PlayerDropItemListener(), this);
        pm.registerEvents(new PlayerInteractAtEntityListener(), this);
        pm.registerEvents(new PlayerInteractListener(), this);
        pm.registerEvents(new PlayerItemConsumeListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerMoveListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerRespawnListener(), this);
        pm.registerEvents(new PlayerTeleportListener(), this);
        pm.registerEvents(new PlayerToggleSneakListener(), this);
        pm.registerEvents(new PlayerBedEnterListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        new Data(PrisonGame.savedPlayerGuards).saveData("saveguard.data");
        bertrude.remove();
        MyTask.bossbar.removeAll();
    }

    public static Location nl(String world, Double X, Double Y, Double Z, Float yaw, Float pitch) {
        return new Location(Bukkit.getWorld(world), X, Y, Z, yaw, pitch);
    }
    public static void setNurse(Player g) {
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Guards").addPlayer(g);
        PrisonGame.roles.put(g, Role.NURSE);
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + g.getName() + " was promoted to a nurse!");

        g.setCustomName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "NURSE" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
        g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "NURSE" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
        g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "NURSE" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());

        ItemStack orangechest = new ItemStack(Material.LEATHER_CHESTPLATE);
        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        LeatherArmorMeta chestmeta = (LeatherArmorMeta) orangechest.getItemMeta();
        chestmeta.setColor(Color.PURPLE);
        orangechest.setItemMeta(chestmeta);

        ItemStack orangeleg = new ItemStack(Material.LEATHER_LEGGINGS);
        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        LeatherArmorMeta orangelegItemMeta = (LeatherArmorMeta) orangeleg.getItemMeta();
        orangelegItemMeta.setColor(Color.PURPLE);
        orangeleg.setItemMeta(orangelegItemMeta);

        ItemStack orangeboot = new ItemStack(Material.LEATHER_BOOTS);
        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        LeatherArmorMeta orangebootItemMeta = (LeatherArmorMeta) orangeboot.getItemMeta();
        orangebootItemMeta.setColor(Color.PURPLE);
        orangeboot.setItemMeta(orangebootItemMeta);

        g.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
        g.getInventory().setChestplate(orangechest);
        g.getInventory().setLeggings(orangeleg);
        g.getInventory().setBoots(orangeboot);
        if (g.getPersistentDataContainer().has(PrisonGame.hg, PersistentDataType.INTEGER)) {
            g.getInventory().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
        }

        ItemStack wardenSword = new ItemStack(Material.STONE_SWORD);
        wardenSword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        wardenSword.addEnchantment(Enchantment.DURABILITY, 1);

        g.getInventory().addItem(wardenSword);

        g.getInventory().addItem(new ItemStack(Material.CROSSBOW));
        g.getInventory().addItem(new ItemStack(Material.ARROW, 16));
        g.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 32));

        ItemStack pot = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta = (PotionMeta) pot.getItemMeta();
        potionMeta.addCustomEffect(PotionEffectType.HEAL.createEffect(10, 2), true);
        pot.setItemMeta(potionMeta);

        g.getInventory().addItem(pot);

        ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta cardm = card.getItemMeta();
        cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
        card.setItemMeta(cardm);
        g.getInventory().addItem(card);

        ItemStack card2 = new ItemStack(Material.IRON_SHOVEL);
        ItemMeta cardm2 = card2.getItemMeta();
        cardm2.setDisplayName(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]");
        cardm2.addEnchant(Enchantment.KNOCKBACK, 1, true);
        card2.setItemMeta(cardm2);
        g.getInventory().addItem(card2);

        if (hardmode.get(g)) {
            String prisonerNumber = "" + new Random().nextInt(100, 999);
            PrisonGame.prisonnumber.put(g, prisonerNumber);
            PlayerDisguise playerDisguise = new PlayerDisguise("Hubertus1703" );
            playerDisguise.setName("NURSE " + prisonerNumber);
            playerDisguise.setKeepDisguiseOnPlayerDeath(true);
            DisguiseAPI.disguiseToAll(g, playerDisguise);
            g.setCustomName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "NURSE " + ChatColor.GRAY + "] " + ChatColor.GRAY + "NURSE" + prisonerNumber);
            g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "HARD MODE" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
            g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "NURSE " + ChatColor.GRAY + "] " + ChatColor.GRAY + "NURSE" + prisonerNumber);
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + g.getName() + " only prison:guard");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + PrisonGame.warden.getName() + " only prison:support");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + g.getName() + " only prison:swat");
        Player nw = (Player) g;
        if (nw.getPersistentDataContainer().has(PrisonGame.protspawn, PersistentDataType.INTEGER)) {
            if (nw.getInventory().getHelmet() != null)
                nw.getInventory().getHelmet().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getChestplate() != null)
                nw.getInventory().getChestplate().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getLeggings() != null)
                nw.getInventory().getLeggings().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getBoots() != null)
                nw.getInventory().getBoots().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        }

    }
    public static void setSwat(Player g) {
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Guards").addPlayer(g);
        PrisonGame.roles.put(g, Role.SWAT);
        Bukkit.broadcastMessage(ChatColor.DARK_GRAY + g.getName() + " was promoted to a SWAT member!");

        g.setCustomName(ChatColor.GRAY + "[" + ChatColor.DARK_GRAY + "SWAT" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
        g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.DARK_GRAY + "SWAT" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
        g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.DARK_GRAY + "SWAT" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());

        ItemStack orangechest = new ItemStack(Material.NETHERITE_CHESTPLATE);

        ItemStack orangeleg = new ItemStack(Material.NETHERITE_LEGGINGS);
        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);


        ItemStack orangeboot = new ItemStack(Material.LEATHER_BOOTS);
        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        LeatherArmorMeta orangelegItemMeta = (LeatherArmorMeta) orangeboot.getItemMeta();
        orangelegItemMeta.setColor(Color.GRAY);
        orangeboot.setItemMeta(orangelegItemMeta);

        g.getInventory().setHelmet(new ItemStack(Material.NETHERITE_HELMET));
        g.getInventory().setChestplate(orangechest);
        g.getInventory().setLeggings(orangeleg);
        g.getInventory().setBoots(orangeboot);

        if (g.getPersistentDataContainer().has(PrisonGame.hg, PersistentDataType.INTEGER)) {
            g.getInventory().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
        }

        ItemStack wardenSword = new ItemStack(Material.DIAMOND_SWORD);

        g.getInventory().addItem(wardenSword);

        g.getInventory().addItem(new ItemStack(Material.BOW));
        g.getInventory().addItem(new ItemStack(Material.ARROW, 16));
        g.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 32));

        ItemStack card2 = new ItemStack(Material.IRON_SHOVEL);
        ItemMeta cardm2 = card2.getItemMeta();
        cardm2.setDisplayName(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]");
        cardm2.addEnchant(Enchantment.KNOCKBACK, 1, true);
        card2.setItemMeta(cardm2);
        g.getInventory().addItem(card2);

        ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta cardm = card.getItemMeta();
        cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
        card.setItemMeta(cardm);
        g.getInventory().addItem(card);

        if (hardmode.get(g)) {
            String prisonerNumber = "" + new Random().nextInt(100, 999);
            PrisonGame.prisonnumber.put(g, prisonerNumber);
            PlayerDisguise playerDisguise = new PlayerDisguise("Hubertus1703");
            playerDisguise.setName("SWAT " + prisonerNumber);
            playerDisguise.setKeepDisguiseOnPlayerDeath(true);
            DisguiseAPI.disguiseToAll(g, playerDisguise);
            g.setCustomName(ChatColor.GRAY + "[" + ChatColor.DARK_GRAY + "SWAT" + ChatColor.GRAY + "] " + ChatColor.GRAY + "SWAT " + prisonerNumber);
            g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "HARD MODE" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
            g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.DARK_GRAY + "SWAT" + ChatColor.GRAY + "] " + ChatColor.GRAY + "SWAT " + prisonerNumber);
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + g.getName() + " only prison:guard");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + PrisonGame.warden.getName() + " only prison:support");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + g.getName() + " only prison:swat");
        Player nw = (Player) g;
        if (nw.getPersistentDataContainer().has(PrisonGame.protspawn, PersistentDataType.INTEGER)) {
            if (nw.getInventory().getHelmet() != null)
                nw.getInventory().getHelmet().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getChestplate() != null)
                nw.getInventory().getChestplate().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getLeggings() != null)
                nw.getInventory().getLeggings().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getBoots() != null)
                nw.getInventory().getBoots().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        }

    }

    public static void setGuard(Player g) {
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Guards").addPlayer(g);
        PrisonGame.roles.put(g, Role.GUARD);
        Bukkit.broadcastMessage(ChatColor.BLUE + g.getName() + " was promoted to a guard!");

        g.setCustomName(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
        g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
        g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());


        ItemStack orangechest = new ItemStack(Material.LEATHER_CHESTPLATE);
        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        LeatherArmorMeta chestmeta = (LeatherArmorMeta) orangechest.getItemMeta();
        chestmeta.setColor(Color.fromRGB(126, 135, 245));
        orangechest.setItemMeta(chestmeta);

        ItemStack orangeleg = new ItemStack(Material.LEATHER_LEGGINGS);
        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        LeatherArmorMeta orangelegItemMeta = (LeatherArmorMeta) orangeleg.getItemMeta();
        orangelegItemMeta.setColor(Color.fromRGB(126, 135, 245));
        orangeleg.setItemMeta(orangelegItemMeta);

        ItemStack orangeboot = new ItemStack(Material.LEATHER_BOOTS);
        orangechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        LeatherArmorMeta orangebootItemMeta = (LeatherArmorMeta) orangeboot.getItemMeta();
        orangebootItemMeta.setColor(Color.fromRGB(126, 135, 245));
        orangeboot.setItemMeta(orangebootItemMeta);

        g.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        g.getInventory().setChestplate(orangechest);
        g.getInventory().setLeggings(orangeleg);
        g.getInventory().setBoots(orangeboot);

        if (g.getPersistentDataContainer().has(PrisonGame.hg, PersistentDataType.INTEGER)) {
            g.getInventory().setBoots(new ItemStack(Material.NETHERITE_BOOTS));
        }

        ItemStack wardenSword = new ItemStack(Material.IRON_SWORD);
        wardenSword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        wardenSword.addEnchantment(Enchantment.DURABILITY, 1);

        g.getInventory().addItem(wardenSword);

        g.getInventory().addItem(new ItemStack(Material.CROSSBOW));
        g.getInventory().addItem(new ItemStack(Material.ARROW, 16));
        g.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));

        ItemStack card2 = new ItemStack(Material.IRON_SHOVEL);
        ItemMeta cardm2 = card2.getItemMeta();
        cardm2.setDisplayName(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]");
        cardm2.addEnchant(Enchantment.KNOCKBACK, 1, true);
        card2.setItemMeta(cardm2);
        g.getInventory().addItem(card2);

        ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta cardm = card.getItemMeta();
        cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
        card.setItemMeta(cardm);
        g.getInventory().addItem(card);

        if (hardmode.get(g)) {
            String prisonerNumber = "" + new Random().nextInt(100, 999);
            PrisonGame.prisonnumber.put(g, prisonerNumber);
            PlayerDisguise playerDisguise = new PlayerDisguise("Hubertus1703");
            playerDisguise.setName("GUARD " + prisonerNumber);
            playerDisguise.setKeepDisguiseOnPlayerDeath(true);
            DisguiseAPI.disguiseToAll(g, playerDisguise);
            g.setCustomName(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + "GUARD " + prisonerNumber);
            g.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "HARD MODE" + ChatColor.GRAY + "] " + ChatColor.GRAY + g.getName());
            g.setDisplayName(ChatColor.GRAY + "[" + ChatColor.BLUE + "GUARD" + ChatColor.GRAY + "] " + ChatColor.GRAY + "GUARD " + prisonerNumber);
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + g.getName() + " only prison:guard");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + PrisonGame.warden.getName() + " only prison:support");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + g.getName() + " only prison:swat");
        Player nw = (Player) g;
        if (nw.getPersistentDataContainer().has(PrisonGame.protspawn, PersistentDataType.INTEGER)) {
            if (nw.getInventory().getHelmet() != null)
                nw.getInventory().getHelmet().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getChestplate() != null)
                nw.getInventory().getChestplate().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getLeggings() != null)
                nw.getInventory().getLeggings().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
            if (nw.getInventory().getBoots() != null)
                nw.getInventory().getBoots().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        }
    }

    public static boolean isInside(Player player, Location loc1, Location loc2)
    {
        double[] dim = new double[2];

        dim[0] = loc1.getX();
        dim[1] = loc2.getX();
        Arrays.sort(dim);
        if(player.getLocation().getX() > dim[1] || player.getLocation().getX() < dim[0])
            return false;

        dim[0] = loc1.getZ();
        dim[1] = loc2.getZ();
        Arrays.sort(dim);
        if(player.getLocation().getZ() > dim[1] || player.getLocation().getZ() < dim[0])
            return false;


        return true;
    }

    public static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

}
