package me.playbosswar.devroomquests.utils;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovementHelpers {
    public static boolean playerMovedToDifferentBlock(PlayerMoveEvent e) {
        Location from = e.getFrom();
        Location to = e.getTo();

        return to.getBlockX() != from.getBlockX() ||
                to.getBlockY() != from.getBlockY() ||
                to.getBlockZ() != from.getBlockZ();
    }
}
