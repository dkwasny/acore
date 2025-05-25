package net.kwas.acore.antlr.resolver.context;

import java.util.List;

public record SpellInfo(
    List<Integer> baseValues,
    List<Integer> dieSides,
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
    int durationPerLevel,
    int maxDuration,
    long procChance,
    long procCharges,
    long maxTargets,
    long maxTargetLevel,
    long cumulativeAura
) {

    public static final SpellInfo EMPTY = new SpellInfo(
        List.of(0, 0, 0),
        List.of(0, 0, 0),
        List.of(0f, 0f, 0f),
        List.of(0L, 0L, 0L),
        List.of(0L, 0L, 0L),
        List.of(0f, 0f, 0f),
        List.of(0, 0, 0),
        List.of(0f, 0f, 0f),
        List.of(0f, 0f, 0f),
        List.of(0f, 0f, 0f),
        List.of(0f, 0f),
        List.of(0f, 0f),
        0,
        0,
        0,
        0L,
        0L,
        0L,
        0L,
        0L
    );
}
