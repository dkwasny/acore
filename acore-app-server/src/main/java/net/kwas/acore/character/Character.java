package net.kwas.acore.character;

public record Character(
    long id,
    long accountId,
    String name,
    String race,
    String chrClass,
    String gender,
    long level,
    long xp,
    long money,
    boolean online,
    long totalTime,
    String zone
) {
}
