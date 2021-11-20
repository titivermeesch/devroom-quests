package me.playbosswar.devroomquests.quests;

import me.playbosswar.devroomquests.Quests;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class QuestManager {
    private List<Quest> quests;

    public QuestManager() {
        loadAllQuests();
    }

    /**
     * Take all present quests in config and load them into memory
     */
    private void loadAllQuests() {
        FileConfiguration fileConfiguration = Quests.getInstance().getConfig();
        this.quests = (List<Quest>) fileConfiguration.getList("quests", new ArrayList<Quest>());
    }
}
