package net.kwas.acore;

import java.util.List;

public record SpellDbc(
    @DbcField(type = DbcType.UINT32)
    Long iD,

    @DbcField(type = DbcType.UINT32)
    Long spellCategory,

    @DbcField(type = DbcType.UINT32)
    Long dispelType,

    @DbcField(type = DbcType.UINT32)
    Long spellMechanic,

    @DbcField(type = DbcType.UINT32)
    Long attributes,

    @DbcField(type = DbcType.UINT32)
    Long attributesEx,

    @DbcField(type = DbcType.UINT32)
    Long attributesExB,

    @DbcField(type = DbcType.UINT32)
    Long attributesExC,

    @DbcField(type = DbcType.UINT32)
    Long attributesExD,

    @DbcField(type = DbcType.UINT32)
    Long attributesExE,

    @DbcField(type = DbcType.UINT32)
    Long attributesExF,

    @DbcField(type = DbcType.UINT32)
    Long attributesExG,

    @DbcField(type = DbcType.UINT32)
    Long stances,

    @DbcField(type = DbcType.UINT32)
    Long unk_320_2,

    @DbcField(type = DbcType.UINT32)
    Long excludedStances,

    @DbcField(type = DbcType.UINT32)
    Long unk_320_3,

    @DbcField(type = DbcType.UINT32)
    Long targets,

    @DbcField(type = DbcType.UINT32)
    Long targetCreatureType,

    @DbcField(type = DbcType.UINT32)
    Long spellFocusObject,

    @DbcField(type = DbcType.UINT32)
    Long facingCasterFlags,

    @DbcField(type = DbcType.UINT32)
    Long casterAuraState,

    @DbcField(type = DbcType.UINT32)
    Long targetAuraState,

    @DbcField(type = DbcType.UINT32)
    Long casterAuraStateNot,

    @DbcField(type = DbcType.UINT32)
    Long targetAuraStateNot,

    @DbcField(type = DbcType.UINT32)
    Long casterAuraSpell,

    @DbcField(type = DbcType.UINT32)
    Long targetAuraSpell,

    @DbcField(type = DbcType.UINT32)
    Long excludeCasterAuraSpell,

    @DbcField(type = DbcType.UINT32)
    Long excludeTargetAuraSpell,

    @DbcField(type = DbcType.UINT32)
    Long castingTimeIndex,

    @DbcField(type = DbcType.UINT32)
    Long recoveryTime,

    @DbcField(type = DbcType.UINT32)
    Long categoryRecoveryTime,

    @DbcField(type = DbcType.UINT32)
    Long interruptFlags,

    @DbcField(type = DbcType.UINT32)
    Long auraInterruptFlags,

    @DbcField(type = DbcType.UINT32)
    Long channelInterruptFlags,

    @DbcField(type = DbcType.UINT32)
    Long procFlags,

    @DbcField(type = DbcType.UINT32)
    Long procChance,

    @DbcField(type = DbcType.UINT32)
    Long procCharges,

    @DbcField(type = DbcType.UINT32)
    Long maxLevel,

    @DbcField(type = DbcType.UINT32)
    Long baseLevel,

    @DbcField(type = DbcType.UINT32)
    Long spellLevel,

    @DbcField(type = DbcType.UINT32)
    Long durationIndex,

    @DbcField(type = DbcType.UINT32)
    Long powerType,

    @DbcField(type = DbcType.UINT32)
    Long manaCost,

    @DbcField(type = DbcType.UINT32)
    Long manaCostPerlevel,

    @DbcField(type = DbcType.UINT32)
    Long manaPerSecond,

    @DbcField(type = DbcType.UINT32)
    Long manaPerSecondPerLevel,

    @DbcField(type = DbcType.UINT32)
    Long rangeIndex,

    @DbcField(type = DbcType.FLOAT)
    Float speed,

    @DbcField(type = DbcType.UINT32)
    Long modalNextSpell,

    @DbcField(type = DbcType.UINT32)
    Long stackAmount,

    @DbcField(type = DbcType.UINT32, count = 2)
    List<Long> totem,

    @DbcField(type = DbcType.INT32, count = 8)
    List<Integer> reagent,

    @DbcField(type = DbcType.UINT32, count = 8)
    List<Long> reagentCount,

    @DbcField(type = DbcType.INT32)
    Integer equippedItemClass,

    @DbcField(type = DbcType.INT32)
    Integer equippedItemSubClassMask,

    @DbcField(type = DbcType.INT32)
    Integer equippedItemInventoryTypeMask,

    @DbcField(type = DbcType.INT32, count = 3)
    List<Integer> effect,

    @DbcField(type = DbcType.INT32, count = 3)
    List<Integer> effectDieSides,

    @DbcField(type = DbcType.FLOAT, count = 3)
    List<Float> effectRealPointsPerLevel,

    @DbcField(type = DbcType.INT32, count = 3)
    List<Integer> effectBasePoints,

    @DbcField(type = DbcType.INT32, count = 3)
    List<Integer> effectMechanic,

    @DbcField(type = DbcType.UINT32, count = 3)
    List<Long> effectImplicitTargetA,

    @DbcField(type = DbcType.UINT32, count = 3)
    List<Long> effectImplicitTargetB,

    @DbcField(type = DbcType.UINT32, count = 3)
    List<Long> effectRadiusIndex,

    @DbcField(type = DbcType.UINT32, count = 3)
    List<Long> effectApplyAuraName,

    @DbcField(type = DbcType.UINT32, count = 3)
    List<Long> effectAuraPeriod,

    @DbcField(type = DbcType.UINT32, count = 3)
    List<Long> effectMultipleValue,

    @DbcField(type = DbcType.UINT32, count = 3)
    List<Long> effectChainTarget,

    @DbcField(type = DbcType.UINT32, count = 3)
    List<Long> effectItemType,

    @DbcField(type = DbcType.INT32, count = 3)
    List<Integer> effectMiscValue,

    @DbcField(type = DbcType.INT32, count = 3)
    List<Integer> effectMiscValueB,

    @DbcField(type = DbcType.UINT32, count = 3)
    List<Long> effectTriggerSpell,

    @DbcField(type = DbcType.FLOAT, count = 3)
    List<Float> effectPointsPerComboPoint,

    @DbcField(type = DbcType.UINT32, count = 3)
    List<Long> effectSpellClassMaskA,

    @DbcField(type = DbcType.UINT32, count = 3)
    List<Long> effectSpellClassMaskB,

    @DbcField(type = DbcType.UINT32, count = 3)
    List<Long> effectSpellClassMaskC,

    @DbcField(type = DbcType.UINT32, count = 2)
    List<Long> spellVisual,

    @DbcField(type = DbcType.UINT32)
    Long spellIconID,

    @DbcField(type = DbcType.UINT32)
    Long activeIconID,

    @DbcField(type = DbcType.UINT32)
    Long spellPriority,

    @DbcField(type = DbcType.STRING_REF)
    DbcStringRef spellName,

    @DbcField(type = DbcType.STRING_REF)
    DbcStringRef spellSubtext,

    @DbcField(type = DbcType.STRING_REF)
    DbcStringRef description,

    @DbcField(type = DbcType.STRING_REF)
    DbcStringRef toolTip,

    @DbcField(type = DbcType.UINT32)
    Long manaCostPercentage,

    @DbcField(type = DbcType.UINT32)
    Long startRecoveryCategory,

    @DbcField(type = DbcType.UINT32)
    Long startRecoveryTime,

    @DbcField(type = DbcType.UINT32)
    Long maxTargetLevel,

    @DbcField(type = DbcType.UINT32)
    Long spellClassSet,

    @DbcField(type = DbcType.UINT32, count = 3)
    List<Long> spellClassMask,

    @DbcField(type = DbcType.UINT32)
    Long maxAffectedTargets,

    @DbcField(type = DbcType.UINT32)
    Long defenseType,

    @DbcField(type = DbcType.UINT32)
    Long preventionType,

    @DbcField(type = DbcType.UINT32)
    Long stanceBarOrder,

    @DbcField(type = DbcType.FLOAT, count = 3)
    List<Float> effectChainAmplitude,

    @DbcField(type = DbcType.UINT32)
    Long minFactionId,

    @DbcField(type = DbcType.UINT32)
    Long minReputation,

    @DbcField(type = DbcType.UINT32)
    Long requiredAuraVision,

    @DbcField(type = DbcType.UINT32, count = 2)
    List<Long> totemCategory,

    @DbcField(type = DbcType.UINT32)
    Long requiredAreaGroupId,

    @DbcField(type = DbcType.UINT32)
    Long schoolMask,

    @DbcField(type = DbcType.UINT32)
    Long runeCostID,

    @DbcField(type = DbcType.UINT32)
    Long spellMissileID,

    @DbcField(type = DbcType.UINT32)
    Long powerDisplayId,

    @DbcField(type = DbcType.FLOAT, count = 3)
    List<Float> effectBonusMultiplier,

    @DbcField(type = DbcType.UINT32)
    Long spellDescriptionVariableID,

    @DbcField(type = DbcType.UINT32)
    Long spellDifficultyId
) {
}
