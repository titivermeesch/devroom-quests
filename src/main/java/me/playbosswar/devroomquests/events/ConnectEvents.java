package me.playbosswar.devroomquests.events;

import me.playbosswar.devroomquests.Quests;
import me.playbosswar.devroomquests.quests.StatsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectEvents implements Listener {
    private final StatsManager statsManager = Quests.getInstance().getStatsManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) { statsManager.registerPlayer(e.getPlayer()); }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) { statsManager.unregisterPlayer(e.getPlayer()); }
}
