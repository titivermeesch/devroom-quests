package me.playbosswar.devroomquests.database;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Represents configuration entered in plugin config file
 */
public class DatabaseConfiguration {
    private final String link;
    private final String collection;
    private final String database;

    public DatabaseConfiguration(FileConfiguration fileConfiguration) {
        this.link = fileConfiguration.getString("mongo");
        this.collection = fileConfiguration.getString("collection");
        this.database = fileConfiguration.getString("database");
    }

    public String getLink() {
        return link;
    }

    public String getCollection() {
        return collection;
    }

    public String getDatabase() {
        return database;
    }

    public boolean isConfigured() { return link != null && collection != null && database != null; }
}
