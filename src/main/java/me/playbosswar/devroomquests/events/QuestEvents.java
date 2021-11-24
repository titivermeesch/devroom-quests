package me.playbosswar.devroomquests.events;

import me.playbosswar.devroomquests.Quests;
import me.playbosswar.devroomquests.quests.EventType;
import me.playbosswar.devroomquests.quests.StatsManager;
import me.playbosswar.devroomquests.utils.MovementHelpers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class QuestEvents implements Listener {
    private final StatsManager statsManager = Quests.getInstance().getStatsManager();

    @EventHandler
    public void onWalk(PlayerMoveEvent e) {
        if (!MovementHelpers.playerMovedToDifferentBlock(e)) {
            return;
        }

        statsManager.registerPlayerEvent(e.getPlayer(), EventType.WALK);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        statsManager.registerPlayerEvent(e.getPlayer(), EventType.BREAK);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        statsManager.registerPlayerEvent(e.getPlayer(), EventType.PLACE);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        statsManager.registerPlayerEvent(e.getPlayer(), EventType.COMMAND);
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent e) {
        Player killer = e.getEntity().getKiller();

        if (killer != null) {
            statsManager.registerPlayerEvent(killer, EventType.KILL_MOB);
        }
    }
}
