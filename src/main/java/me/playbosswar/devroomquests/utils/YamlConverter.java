package me.playbosswar.devroomquests.utils;

import me.playbosswar.devroomquests.quests.EventType;
import me.playbosswar.devroomquests.quests.Quest;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class YamlConverter {
    public static Quest getQuestFromConfigurationSection(ConfigurationSection configurationSection, String key) {
        EventType type = EventType.valueOf(configurationSection.getString(key + ".type"));
        int threshold = configurationSection.getInt(key + ".threshold");
        List<String> commands = configurationSection.getStringList(key + ".commands");

        return new Quest(type, threshold, commands);
    }
}
