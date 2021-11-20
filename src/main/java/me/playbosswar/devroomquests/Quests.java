package me.playbosswar.devroomquests;

import me.playbosswar.devroomquests.quests.QuestManager;
import me.playbosswar.devroomquests.quests.StatsManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Quests extends JavaPlugin {
    private static Quests instance;
    private QuestManager questManager;
    private StatsManager statsManager;

    @Override
    public void onEnable() {
        instance = this;
        setupConfig();

        this.questManager = new QuestManager();
        this.statsManager = new StatsManager();
    }

    private void setupConfig() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
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
