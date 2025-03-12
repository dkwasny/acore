package net.kwas.acore.spell;

public record Spell(
    long id,
    String name,
    String subtext,
    String description,
    String iconUrl
) {
}
