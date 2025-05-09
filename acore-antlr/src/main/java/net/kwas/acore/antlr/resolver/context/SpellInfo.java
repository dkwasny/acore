package net.kwas.acore.antlr.resolver.context;

import java.util.List;

public record SpellInfo(
    List<Integer> baseValues,
    List<Integer> dieSides,
    List<Float> coefficients,
    int duration
) {
}
