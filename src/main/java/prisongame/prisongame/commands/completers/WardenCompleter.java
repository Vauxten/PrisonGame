package prisongame.prisongame.commands.completers;

import prisongame.prisongame.lib.Config;

public class WardenCompleter extends SubcommandCompleter {
    public WardenCompleter() {
        super("warden", Config.Warden.help.keySet().toArray(new String[0]));
    }
}