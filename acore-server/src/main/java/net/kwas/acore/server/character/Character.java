package net.kwas.acore.server.character;

import net.kwas.acore.common.Gender;

public record Character(
    long id,
    long accountId,
    String name,
    String race,
    String chrClass,
    Gender gender,
    long level,
    long xp,
    long money,
    boolean online,
    long totalTime,
    String zone
) {
}
