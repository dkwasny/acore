package net.kwas.acore.antlr.resolver.context;

import java.util.List;

/**
 * This builder was generated with IntelliJ's Replace Constructor with Builder functionality.
 */
public class SpellInfoBuilder {
  private List<Integer> baseValues;
  private List<Integer> dieSides;
  private List<Float> baseValuePerLevels;
  private List<Long> auraPeriods;
  private List<Long> chainTargets;
  private List<Float> radii;
  private List<Integer> miscValues;
  private List<Float> pointsPerCombo;
  private List<Float> amplitudes;
  private List<Float> chainAmplitudes;
  private List<Float> minRanges;
  private List<Float> maxRanges;
  private long baseLevel;
  private long maxLevel;
  private int duration;
  private int durationPerLevel;
  private int maxDuration;
  private long procChance;
  private long procCharges;
  private long maxTargets;
  private long maxTargetLevel;
  private long cumulativeAura;

  public SpellInfoBuilder baseValues(List<Integer> baseValues) {
    this.baseValues = baseValues;
    return this;
  }

  public SpellInfoBuilder dieSides(List<Integer> dieSides) {
    this.dieSides = dieSides;
    return this;
  }

  public SpellInfoBuilder baseValuePerLevels(List<Float> baseValuePerLevels) {
    this.baseValuePerLevels = baseValuePerLevels;
    return this;
  }

  public SpellInfoBuilder auraPeriods(List<Long> auraPeriods) {
    this.auraPeriods = auraPeriods;
    return this;
  }

  public SpellInfoBuilder chainTargets(List<Long> chainTargets) {
    this.chainTargets = chainTargets;
    return this;
  }

  public SpellInfoBuilder radii(List<Float> radii) {
    this.radii = radii;
    return this;
  }

  public SpellInfoBuilder miscValues(List<Integer> miscValues) {
    this.miscValues = miscValues;
    return this;
  }

  public SpellInfoBuilder pointsPerCombo(List<Float> pointsPerCombo) {
    this.pointsPerCombo = pointsPerCombo;
    return this;
  }

  public SpellInfoBuilder amplitudes(List<Float> amplitudes) {
    this.amplitudes = amplitudes;
    return this;
  }

  public SpellInfoBuilder chainAmplitudes(List<Float> chainAmplitudes) {
    this.chainAmplitudes = chainAmplitudes;
    return this;
  }

  public SpellInfoBuilder minRanges(List<Float> minRanges) {
    this.minRanges = minRanges;
    return this;
  }

  public SpellInfoBuilder maxRanges(List<Float> maxRanges) {
    this.maxRanges = maxRanges;
    return this;
  }

  public SpellInfoBuilder baseLevel(long baseLevel) {
    this.baseLevel = baseLevel;
    return this;
  }

  public SpellInfoBuilder maxLevel(long maxLevel) {
    this.maxLevel = maxLevel;
    return this;
  }

  public SpellInfoBuilder duration(int duration) {
    this.duration = duration;
    return this;
  }

  public SpellInfoBuilder durationPerLevel(int durationPerLevel) {
    this.durationPerLevel = durationPerLevel;
    return this;
  }

  public SpellInfoBuilder maxDuration(int maxDuration) {
    this.maxDuration = maxDuration;
    return this;
  }

  public SpellInfoBuilder procChance(long procChance) {
    this.procChance = procChance;
    return this;
  }

  public SpellInfoBuilder procCharges(long procCharges) {
    this.procCharges = procCharges;
    return this;
  }

  public SpellInfoBuilder maxTargets(long maxTargets) {
    this.maxTargets = maxTargets;
    return this;
  }

  public SpellInfoBuilder maxTargetLevel(long maxTargetLevel) {
    this.maxTargetLevel = maxTargetLevel;
    return this;
  }

  public SpellInfoBuilder cumulativeAura(long cumulativeAura) {
    this.cumulativeAura = cumulativeAura;
    return this;
  }

  public SpellInfo createSpellInfo() {
    return new SpellInfo(
      baseValues,
      dieSides,
      baseValuePerLevels,
      auraPeriods,
      chainTargets,
      radii,
      miscValues,
      pointsPerCombo,
      amplitudes,
      chainAmplitudes,
      minRanges,
      maxRanges,
      baseLevel,
      maxLevel,
      duration,
      durationPerLevel,
      maxDuration,
      procChance,
      procCharges,
      maxTargets,
      maxTargetLevel,
      cumulativeAura
    );
  }
}
