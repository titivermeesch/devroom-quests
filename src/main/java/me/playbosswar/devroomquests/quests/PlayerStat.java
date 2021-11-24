package me.playbosswar.devroomquests.quests;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.UUID;

public class PlayerStat {
    @BsonId
    private ObjectId id;
    @BsonProperty("playerUuid")
    private UUID playerUuid;
    @BsonProperty("eventType")
    private EventType eventType;
    @BsonProperty("amount")
    private int amount = 0;

    // Required for POJO conversion
    public PlayerStat() {
    }

    public PlayerStat(UUID playerUuid, EventType eventType) {
        this.playerUuid = playerUuid;
        this.eventType = eventType;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public EventType getEventType() {
        return eventType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPlayerUuid(UUID playerUuid) {
        this.playerUuid = playerUuid;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
