package me.playbosswar.devroomquests.quests;

import me.playbosswar.devroomquests.Quests;
import me.playbosswar.devroomquests.database.Database;
import me.playbosswar.devroomquests.logger.LogDebug;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class StatsManager {
    private final Database database;
    private final QuestManager questManager;
    private List<PlayerStat> playerStats = new ArrayList<>();
    private Timer scheduledDatabaseSync;

    public StatsManager(Database database) {
        this.database = database;
        this.questManager = Quests.getInstance().getQuestManager();
        createSyncTimer();
    }

    private void createSyncTimer() {
        scheduledDatabaseSync = new Timer();
        scheduledDatabaseSync.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() { syncLocalDataWithDatabase(); }
        }, 0, 60000);
    }

    /**
     * Cleanup remaining data and stop automatic syncing
     */
    public void onDisable() {
        syncLocalDataWithDatabase();
        scheduledDatabaseSync.cancel();
    }

    /**
     * Find a specific stat for a player and a type
     *
     * @param p    - Player to get stat from
     * @param type -
     *
     * @return -
     */
    public PlayerStat getPlayerStatForPlayer(Player p, EventType type) {
        return playerStats
                .stream()
                .filter(stat -> stat.getPlayerUuid().equals(p.getUniqueId()) && stat.getEventType().equals(type))
                .findFirst()
                .orElse(null);
    }

    /**
     * Register a certain event a user executed (one of EventType)
     *
     * @param p    - Player that triggered the event
     * @param type - Type of the event
     */
    public void registerPlayerEvent(Player p, EventType type) {
        LogDebug.m("Registering event " + type.toString() + " for player " + p.getDisplayName());
        this.playerStats = playerStats.stream().map(stat -> {
            if (stat.getPlayerUuid().equals(p.getUniqueId()) && stat.getEventType().equals(type)) {
                stat.setAmount(stat.getAmount() + 1);
                return stat;
            }

            return stat;
        }).collect(Collectors.toList());

        questManager.executeQuests(questManager.getFulfilledQuests(p, type), p);
    }

    /**
     * Create a list of empty player stat objects for a player who does not have any stats yet
     *
     * @param p - Player to create stats for
     *
     * @return -
     */
    private List<PlayerStat> createEmptyPlayerStatsForPlayer(Player p) {
        List<PlayerStat> playerStats = new ArrayList<>();

        for (EventType eventType : EventType.values()) {
            playerStats.add(new PlayerStat(p.getUniqueId(), eventType));
        }

        return playerStats;
    }

    /**
     * Add player to loaded stats and create new database records if the user does not have any yet
     *
     * @param p - Player to register
     */
    public void registerPlayer(Player p) {
        LogDebug.m("Registering player " + p.getDisplayName());
        Document query = new Document("playerUuid", p.getUniqueId());
        List<PlayerStat> playerStats = database.findAll(query);

        if (playerStats.size() == 0) {
            LogDebug.m("Player is not registered in the DB yet, creating empty entries for " + p.getDisplayName());
            List<PlayerStat> emptyPlayerStats = createEmptyPlayerStatsForPlayer(p);
            this.playerStats.addAll(emptyPlayerStats);
            database.insertMany(emptyPlayerStats);
        } else {
            LogDebug.m("Player has already existing entries, saving in local memory for " + p.getDisplayName());
            this.playerStats.addAll(playerStats);
        }
    }

    /**
     * Remove player from loaded player stats
     *
     * @param p - Player to unload
     */
    public void unregisterPlayer(Player p) {
        LogDebug.m("Unregistering player " + p.getDisplayName());
        this.playerStats =
                playerStats.stream().filter(stat -> !stat.getPlayerUuid().equals(p.getUniqueId())).collect(Collectors.toList());
    }

    /**
     * Save local data in database
     */
    private void syncLocalDataWithDatabase() {
        LogDebug.m("Syncing stats with database");
        this.playerStats.forEach(database::replace);
    }
}
