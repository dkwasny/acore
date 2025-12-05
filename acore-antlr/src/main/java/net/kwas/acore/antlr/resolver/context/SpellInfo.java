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
  long baseLevel,
  long maxLevel,
  int duration,
  int durationPerLevel,
  int maxDuration,
  long procChance,
  long procCharges,
  long maxTargets,
  long maxTargetLevel,
  long cumulativeAura
) {

  // Some spells reference other spells that do not exist in the DBC data.
  // One example is Great Stamina (4195) that references a version of
  // Great Stamina (4187) which is not present in the WotLK DBC files.
  // These are likely old spells that have been superseded by newer
  // versions (e.g. 61686 for Great Stamina).
  // This empty spell info is used in such cases.
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
    0,
    0,
    0L,
    0L,
    0L,
    0L,
    0L
  );

}
