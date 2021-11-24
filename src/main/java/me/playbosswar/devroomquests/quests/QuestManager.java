package me.playbosswar.devroomquests.quests;

import me.playbosswar.devroomquests.Quests;
import me.playbosswar.devroomquests.logger.LogDebug;
import me.playbosswar.devroomquests.utils.YamlConverter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class QuestManager {
    private final List<Quest> quests = new ArrayList<>();

    public QuestManager() {
        loadAllQuests();
    }

    /**
     * Take all present quests in config and load them into memory
     */
    private void loadAllQuests() {
        FileConfiguration fileConfiguration = Quests.getInstance().getConfig();
        ConfigurationSection questsSection = fileConfiguration.getConfigurationSection("quests");

        // Player did not fill in any quests yet
        if (questsSection == null) {
            return;
        }

        Set<String> keys = questsSection.getKeys(false);
        for (String key : keys) {
            this.quests.add(YamlConverter.getQuestFromConfigurationSection(questsSection, key));
        }
    }

    /**
     * Run commands linked to a quest for a specific player
     *
     * @param quest - Quest to execute
     * @param p     - Player to execute the quest for
     */
    public void executeQuest(Quest quest, Player p) {
        List<String> parsedCommands = quest
                .commands()
                .stream()
                .map(command -> command.replaceAll("%player_name%", p.getDisplayName()))
                .collect(Collectors.toList());
        parsedCommands.forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
    }

    /**
     * Execute a list of quests for a specific player
     *
     * @param quests - Quests to execute
     * @param p      - Player to execute the quests for
     */
    public void executeQuests(List<Quest> quests, Player p) { quests.forEach(quest -> executeQuest(quest, p)); }

    /**
     * Get all quests that can be fulfilled for user
     *
     * @param p    - Player to check for
     * @param type - Quest type
     *
     * @return -
     */
    public List<Quest> getFulfilledQuests(Player p, EventType type) {
        PlayerStat playerStat = Quests.getInstance().getStatsManager().getPlayerStatForPlayer(p, type);

        if (playerStat == null) {
            LogDebug.m("Failed to find player stat for player " + p.getDisplayName() + " and type " + type);
            return new ArrayList<>();
        }

        return quests
                .stream()
                .filter(quest -> quest.type().equals(type) && quest.threshold() == playerStat.getAmount())
                .collect(Collectors.toList());
    }
}
