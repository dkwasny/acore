package net.kwas.acore.antlr.resolver.context;

import java.util.Set;

public record CharacterInfo(
    long characterLevel,
    String gender,
    Set<Long> learnedSpellIds
) {
}
