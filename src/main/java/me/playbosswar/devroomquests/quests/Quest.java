package me.playbosswar.devroomquests.quests;

import java.util.List;

public record Quest(EventType type, int threshold, List<String> commands) {}
