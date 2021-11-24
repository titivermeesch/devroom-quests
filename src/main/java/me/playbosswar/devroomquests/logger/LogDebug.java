package me.playbosswar.devroomquests.logger;

import me.playbosswar.devroomquests.Quests;
import org.bukkit.Bukkit;

public class LogDebug {
    private static final Boolean enabled = Quests.getInstance().getConfig().getBoolean("debug");

    public static void m(String message) {
        if (enabled) {
            Bukkit.getConsoleSender().sendMessage("§6§l[DEBUG] §f" + message);
        }
    }
}
