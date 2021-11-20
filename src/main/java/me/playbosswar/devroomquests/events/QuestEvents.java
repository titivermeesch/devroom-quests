package me.playbosswar.devroomquests.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class QuestEvents implements Listener {
    @EventHandler
    public void onWalk(PlayerMoveEvent e) {

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {

    }

    @EventHandler
    public void onCommand(PlayerCommandSendEvent e) {

    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent e) {

    }
}
