package net.kwas.acore.server.spell;

public record Spell(
    long id,
    String name,
    String subtext,
    String description,
    String iconUrl
) {
}
