package net.kwas.acore.server.spell;

// TODO: Think of a better name.
// This is an internally used cache record for static spell information.
// Not all fields should be returned to the UI.
public record SpellSummary(
    long id,
    String name,
    String subtext,
    String rawDescription,
    String iconUrl,
    int spellDescriptionVariablesId
) {
}
