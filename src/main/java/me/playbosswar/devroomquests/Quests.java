package me.playbosswar.devroomquests;

import me.playbosswar.devroomquests.database.Database;
import me.playbosswar.devroomquests.events.ConnectEvents;
import me.playbosswar.devroomquests.events.QuestEvents;
import me.playbosswar.devroomquests.quests.QuestManager;
import me.playbosswar.devroomquests.quests.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Quests extends JavaPlugin {
    private static Quests instance;
    private QuestManager questManager;
    private StatsManager statsManager;
    private Database database;

    @Override
    public void onEnable() {
        instance = this;
        setupConfig();

        this.database = new Database();
        this.questManager = new QuestManager();
        this.statsManager = new StatsManager(database);
        registerEvents();
        registerOnlinePlayers();
    }

    @Override
    public void onDisable() {
        statsManager.onDisable();
        database.close();
    }

    /**
     * Needed for reload support. On reload, the stats manager will lose all data,
     * so we need to repopulate that when server starts (for players that are online)
     */
    private void registerOnlinePlayers() {
        Bukkit.getOnlinePlayers().forEach(p -> statsManager.registerPlayer(p));
    }

    /**
     * Load default yaml configuration file
     */
    private void setupConfig() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ConnectEvents(), this);
        pm.registerEvents(new QuestEvents(), this);
    }

    public static Quests getInstance() {
        return instance;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }
}
