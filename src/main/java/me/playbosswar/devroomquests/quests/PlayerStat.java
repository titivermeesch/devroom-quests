package me.playbosswar.devroomquests.quests;

import org.bukkit.entity.Player;

public class PlayerStat {
    private final Player player;
    private final EventType eventType;
    private int amount = 0;

    public PlayerStat(Player player, EventType eventType) {
        this.player = player;
        this.eventType = eventType;
    }

    public Player getPlayer() {
        return player;
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
}
