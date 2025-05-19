package net.kwas.acore.antlr.resolver.context;

import java.util.List;

public record SpellInfo(
    List<Integer> baseValues,
    List<Integer> dieSides,
    List<Float> coefficients,
    List<Float> baseValuePerLevels,
    List<Long> auraPeriods,
    int duration
) {
}
