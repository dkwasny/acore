package net.kwas.acore.antlr.resolver.context;

import java.util.List;

public record SpellInfo(
    List<Integer> baseValues,
    List<Integer> dieSides,
    List<Float> coefficients,
    List<Float> baseValuePerLevels,
    List<Long> auraPeriods,
    List<Long> chainTargets,
    List<Float> radii,
    List<Integer> miscValues,
    List<Float> pointsPerCombo,
    List<Float> amplitudes,
    List<Float> chainAmplitudes,
    List<Float> minRanges,
    List<Float> maxRanges,
    int duration,
    long procChance,
    long procCharges,
    long maxTargets,
    long maxTargetLevel,
    long cumulativeAura
) {
}
