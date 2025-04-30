package net.kwas.acore.antlr;

import java.util.List;
import java.util.Map;

public record SpellContext(
    long spellId,
    Map<Integer, List<Double>> multipliers
) {
}
