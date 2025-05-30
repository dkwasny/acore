package net.kwas.acore.server.spell;

public record RawSpell(
    long id,
    String name,
    String subtext,
    String rawDescription,
    String iconUrl,
    int spellDescriptionVariablesId
) {
}
