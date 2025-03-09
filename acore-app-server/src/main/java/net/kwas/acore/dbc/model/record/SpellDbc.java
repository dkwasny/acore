package net.kwas.acore.dbc.model.record;

import net.kwas.acore.dbc.model.DbcField;
import net.kwas.acore.dbc.model.DbcType;

public class SpellDbc {

    @DbcField(DbcType.UINT32)
    public long id;

    @DbcField(DbcType.UINT32)
    public long category;

    @DbcField(DbcType.UINT32)
    public long dispelType;

    @DbcField(DbcType.UINT32)
    public long mechanic;

    @DbcField(DbcType.UINT32)
    public long attributes;

    @DbcField(DbcType.UINT32)
    public long attributesEx;

    @DbcField(DbcType.UINT32)
    public long attributesExB;

    @DbcField(DbcType.UINT32)
    public long attributesExC;

    @DbcField(DbcType.UINT32)
    public long attributesExD;

    @DbcField(DbcType.UINT32)
    public long attributesExE;

    @DbcField(DbcType.UINT32)
    public long attributesExF;

    @DbcField(DbcType.UINT32)
    public long attributesExG;

    @DbcField(DbcType.UINT32)
    public long shapeshiftMask0;

    @DbcField(DbcType.UINT32)
    public long shapeshiftMask1;

    @DbcField(DbcType.UINT32)
    public long shapeshiftExclude0;

    @DbcField(DbcType.UINT32)
    public long shapeshiftExclude1;

    @DbcField(DbcType.UINT32)
    public long targets;

    @DbcField(DbcType.UINT32)
    public long targetCreatureType;

    @DbcField(DbcType.UINT32)
    public long requiresSpellFocus;

    @DbcField(DbcType.UINT32)
    public long facingCasterFlags;

    @DbcField(DbcType.UINT32)
    public long casterAuraState;

    @DbcField(DbcType.UINT32)
    public long targetAuraState;

    @DbcField(DbcType.UINT32)
    public long excludeCasterAuraState;

    @DbcField(DbcType.UINT32)
    public long excludeTargetAuraState;

    @DbcField(DbcType.UINT32)
    public long casterAuraSpell;

    @DbcField(DbcType.UINT32)
    public long targetAuraSpell;

    @DbcField(DbcType.UINT32)
    public long excludeCasterAuraSpell;

    @DbcField(DbcType.UINT32)
    public long excludeTargetAuraSpell;

    @DbcField(DbcType.UINT32)
    public long castingTimeIndex;

    @DbcField(DbcType.UINT32)
    public long recoveryTime;

    @DbcField(DbcType.UINT32)
    public long categoryRecoveryTime;

    @DbcField(DbcType.UINT32)
    public long interruptFlags;

    @DbcField(DbcType.UINT32)
    public long auraInterruptFlags;

    @DbcField(DbcType.UINT32)
    public long channelInterruptFlags;

    @DbcField(DbcType.UINT32)
    public long procTypeMask;

    @DbcField(DbcType.UINT32)
    public long procChance;

    @DbcField(DbcType.UINT32)
    public long procCharges;

    @DbcField(DbcType.UINT32)
    public long maxLevel;

    @DbcField(DbcType.UINT32)
    public long baseLevel;

    @DbcField(DbcType.UINT32)
    public long spellLevel;

    @DbcField(DbcType.UINT32)
    public long durationIndex;

    @DbcField(DbcType.INT32)
    public int powerType;

    @DbcField(DbcType.UINT32)
    public long manaCost;

    @DbcField(DbcType.UINT32)
    public long manaCostPerLevel;

    @DbcField(DbcType.UINT32)
    public long manaPerSecond;

    @DbcField(DbcType.UINT32)
    public long manaPerSecondPerLevel;

    @DbcField(DbcType.UINT32)
    public long rangeIndex;

    @DbcField(DbcType.FLOAT)
    public float speed;

    @DbcField(DbcType.UINT32)
    public long modalNextSpell;

    @DbcField(DbcType.UINT32)
    public long cumulativeAura;

    @DbcField(DbcType.UINT32)
    public long totem0;

    @DbcField(DbcType.UINT32)
    public long totem1;

    @DbcField(DbcType.INT32)
    public int reagent0;

    @DbcField(DbcType.INT32)
    public int reagent1;

    @DbcField(DbcType.INT32)
    public int reagent2;

    @DbcField(DbcType.INT32)
    public int reagent3;

    @DbcField(DbcType.INT32)
    public int reagent4;

    @DbcField(DbcType.INT32)
    public int reagent5;

    @DbcField(DbcType.INT32)
    public int reagent6;

    @DbcField(DbcType.INT32)
    public int reagent7;

    @DbcField(DbcType.UINT32)
    public long reagentCount0;

    @DbcField(DbcType.UINT32)
    public long reagentCount1;

    @DbcField(DbcType.UINT32)
    public long reagentCount2;

    @DbcField(DbcType.UINT32)
    public long reagentCount3;

    @DbcField(DbcType.UINT32)
    public long reagentCount4;

    @DbcField(DbcType.UINT32)
    public long reagentCount5;

    @DbcField(DbcType.UINT32)
    public long reagentCount6;

    @DbcField(DbcType.UINT32)
    public long reagentCount7;

    @DbcField(DbcType.INT32)
    public int equippedItemClass;

    @DbcField(DbcType.INT32)
    public int equippedItemSubclass;

    @DbcField(DbcType.INT32)
    public int equippedItemInvTypes;

    @DbcField(DbcType.UINT32)
    public long effect0;

    @DbcField(DbcType.UINT32)
    public long effect1;

    @DbcField(DbcType.UINT32)
    public long effect2;

    @DbcField(DbcType.INT32)
    public int effectDieSides0;

    @DbcField(DbcType.INT32)
    public int effectDieSides1;

    @DbcField(DbcType.INT32)
    public int effectDieSides2;

    @DbcField(DbcType.FLOAT)
    public float effectRealPointsPerLevel0;

    @DbcField(DbcType.FLOAT)
    public float effectRealPointsPerLevel1;

    @DbcField(DbcType.FLOAT)
    public float effectRealPointsPerLevel2;

    @DbcField(DbcType.INT32)
    public int effectBasePoints0;

    @DbcField(DbcType.INT32)
    public int effectBasePoints1;

    @DbcField(DbcType.INT32)
    public int effectBasePoints2;

    @DbcField(DbcType.UINT32)
    public long effectMechanic0;

    @DbcField(DbcType.UINT32)
    public long effectMechanic1;

    @DbcField(DbcType.UINT32)
    public long effectMechanic2;

    @DbcField(DbcType.UINT32)
    public long effectImplicitTargetA0;

    @DbcField(DbcType.UINT32)
    public long effectImplicitTargetA1;

    @DbcField(DbcType.UINT32)
    public long effectImplicitTargetA2;

    @DbcField(DbcType.UINT32)
    public long effectImplicitTargetB0;

    @DbcField(DbcType.UINT32)
    public long effectImplicitTargetB1;

    @DbcField(DbcType.UINT32)
    public long effectImplicitTargetB2;

    @DbcField(DbcType.UINT32)
    public long effectRadiusIndex0;

    @DbcField(DbcType.UINT32)
    public long effectRadiusIndex1;

    @DbcField(DbcType.UINT32)
    public long effectRadiusIndex2;

    @DbcField(DbcType.UINT32)
    public long effectAura0;

    @DbcField(DbcType.UINT32)
    public long effectAura1;

    @DbcField(DbcType.UINT32)
    public long effectAura2;

    @DbcField(DbcType.UINT32)
    public long effectAuraPeriod0;

    @DbcField(DbcType.UINT32)
    public long effectAuraPeriod1;

    @DbcField(DbcType.UINT32)
    public long effectAuraPeriod2;

    @DbcField(DbcType.FLOAT)
    public float effectAmplitude0;

    @DbcField(DbcType.FLOAT)
    public float effectAmplitude1;

    @DbcField(DbcType.FLOAT)
    public float effectAmplitude2;

    @DbcField(DbcType.UINT32)
    public long effectChainTargets0;

    @DbcField(DbcType.UINT32)
    public long effectChainTargets1;

    @DbcField(DbcType.UINT32)
    public long effectChainTargets2;

    @DbcField(DbcType.UINT32)
    public long effectItemType0;

    @DbcField(DbcType.UINT32)
    public long effectItemType1;

    @DbcField(DbcType.UINT32)
    public long effectItemType2;

    @DbcField(DbcType.INT32)
    public int effectMiscValue0;

    @DbcField(DbcType.INT32)
    public int effectMiscValue1;

    @DbcField(DbcType.INT32)
    public int effectMiscValue2;

    @DbcField(DbcType.INT32)
    public int effectMiscValueB0;

    @DbcField(DbcType.INT32)
    public int effectMiscValueB1;

    @DbcField(DbcType.INT32)
    public int effectMiscValueB2;

    @DbcField(DbcType.INT32)
    public int effectTriggerSpell0;

    @DbcField(DbcType.INT32)
    public int effectTriggerSpell1;

    @DbcField(DbcType.INT32)
    public int effectTriggerSpell2;

    @DbcField(DbcType.FLOAT)
    public float effectPointsPerCombo0;

    @DbcField(DbcType.FLOAT)
    public float effectPointsPerCombo1;

    @DbcField(DbcType.FLOAT)
    public float effectPointsPerCombo2;

    @DbcField(DbcType.UINT32)
    public long effectSpellClassMaskA0;

    @DbcField(DbcType.UINT32)
    public long effectSpellClassMaskA1;

    @DbcField(DbcType.UINT32)
    public long effectSpellClassMaskA2;

    @DbcField(DbcType.UINT32)
    public long effectSpellClassMaskB0;

    @DbcField(DbcType.UINT32)
    public long effectSpellClassMaskB1;

    @DbcField(DbcType.UINT32)
    public long effectSpellClassMaskB2;

    @DbcField(DbcType.UINT32)
    public long effectSpellClassMaskC0;

    @DbcField(DbcType.UINT32)
    public long effectSpellClassMaskC1;

    @DbcField(DbcType.UINT32)
    public long effectSpellClassMaskC2;

    @DbcField(DbcType.UINT32)
    public long spellVisualId0;

    @DbcField(DbcType.UINT32)
    public long spellVisualId1;

    @DbcField(DbcType.UINT32)
    public long spellIconId;

    @DbcField(DbcType.UINT32)
    public long activeIconId;

    @DbcField(DbcType.UINT32)
    public long spellPriority;

    @DbcField(DbcType.STRING)
    public String name0;

    @DbcField(DbcType.STRING)
    public String name1;

    @DbcField(DbcType.STRING)
    public String name2;

    @DbcField(DbcType.STRING)
    public String name3;

    @DbcField(DbcType.STRING)
    public String name4;

    @DbcField(DbcType.STRING)
    public String name5;

    @DbcField(DbcType.STRING)
    public String name6;

    @DbcField(DbcType.STRING)
    public String name7;

    @DbcField(DbcType.STRING)
    public String name8;

    @DbcField(DbcType.STRING)
    public String name9;

    @DbcField(DbcType.STRING)
    public String name10;

    @DbcField(DbcType.STRING)
    public String name11;

    @DbcField(DbcType.STRING)
    public String name12;

    @DbcField(DbcType.STRING)
    public String name13;

    @DbcField(DbcType.STRING)
    public String name14;

    @DbcField(DbcType.STRING)
    public String name15;

    @DbcField(DbcType.UINT32)
    public long namelangmask;

    @DbcField(DbcType.STRING)
    public String nameSubtext0;

    @DbcField(DbcType.STRING)
    public String nameSubtext1;

    @DbcField(DbcType.STRING)
    public String nameSubtext2;

    @DbcField(DbcType.STRING)
    public String nameSubtext3;

    @DbcField(DbcType.STRING)
    public String nameSubtext4;

    @DbcField(DbcType.STRING)
    public String nameSubtext5;

    @DbcField(DbcType.STRING)
    public String nameSubtext6;

    @DbcField(DbcType.STRING)
    public String nameSubtext7;

    @DbcField(DbcType.STRING)
    public String nameSubtext8;

    @DbcField(DbcType.STRING)
    public String nameSubtext9;

    @DbcField(DbcType.STRING)
    public String nameSubtext10;

    @DbcField(DbcType.STRING)
    public String nameSubtext11;

    @DbcField(DbcType.STRING)
    public String nameSubtext12;

    @DbcField(DbcType.STRING)
    public String nameSubtext13;

    @DbcField(DbcType.STRING)
    public String nameSubtext14;

    @DbcField(DbcType.STRING)
    public String nameSubtext15;

    @DbcField(DbcType.UINT32)
    public long nameSubtextlangmask;

    @DbcField(DbcType.STRING)
    public String description0;

    @DbcField(DbcType.STRING)
    public String description1;

    @DbcField(DbcType.STRING)
    public String description2;

    @DbcField(DbcType.STRING)
    public String description3;

    @DbcField(DbcType.STRING)
    public String description4;

    @DbcField(DbcType.STRING)
    public String description5;

    @DbcField(DbcType.STRING)
    public String description6;

    @DbcField(DbcType.STRING)
    public String description7;

    @DbcField(DbcType.STRING)
    public String description8;

    @DbcField(DbcType.STRING)
    public String description9;

    @DbcField(DbcType.STRING)
    public String description10;

    @DbcField(DbcType.STRING)
    public String description11;

    @DbcField(DbcType.STRING)
    public String description12;

    @DbcField(DbcType.STRING)
    public String description13;

    @DbcField(DbcType.STRING)
    public String description14;

    @DbcField(DbcType.STRING)
    public String description15;

    @DbcField(DbcType.UINT32)
    public long descriptionlangmask;

    @DbcField(DbcType.STRING)
    public String auraDescription0;

    @DbcField(DbcType.STRING)
    public String auraDescription1;

    @DbcField(DbcType.STRING)
    public String auraDescription2;

    @DbcField(DbcType.STRING)
    public String auraDescription3;

    @DbcField(DbcType.STRING)
    public String auraDescription4;

    @DbcField(DbcType.STRING)
    public String auraDescription5;

    @DbcField(DbcType.STRING)
    public String auraDescription6;

    @DbcField(DbcType.STRING)
    public String auraDescription7;

    @DbcField(DbcType.STRING)
    public String auraDescription8;

    @DbcField(DbcType.STRING)
    public String auraDescription9;

    @DbcField(DbcType.STRING)
    public String auraDescription10;

    @DbcField(DbcType.STRING)
    public String auraDescription11;

    @DbcField(DbcType.STRING)
    public String auraDescription12;

    @DbcField(DbcType.STRING)
    public String auraDescription13;

    @DbcField(DbcType.STRING)
    public String auraDescription14;

    @DbcField(DbcType.STRING)
    public String auraDescription15;

    @DbcField(DbcType.UINT32)
    public long auraDescriptionlangmask;

    @DbcField(DbcType.UINT32)
    public long manaCostPct;

    @DbcField(DbcType.UINT32)
    public long startRecoveryCategory;

    @DbcField(DbcType.UINT32)
    public long startRecoveryTime;

    @DbcField(DbcType.UINT32)
    public long maxTargetLevel;

    @DbcField(DbcType.UINT32)
    public long spellClassSet;

    @DbcField(DbcType.UINT32)
    public long spellClassMask0;

    @DbcField(DbcType.UINT32)
    public long spellClassMask1;

    @DbcField(DbcType.UINT32)
    public long spellClassMask2;

    @DbcField(DbcType.UINT32)
    public long maxTargets;

    @DbcField(DbcType.UINT32)
    public long defenseType;

    @DbcField(DbcType.UINT32)
    public long preventionType;

    @DbcField(DbcType.INT32)
    public int stanceBarOrder;

    @DbcField(DbcType.FLOAT)
    public float effectChainAmplitude0;

    @DbcField(DbcType.FLOAT)
    public float effectChainAmplitude1;

    @DbcField(DbcType.FLOAT)
    public float effectChainAmplitude2;

    @DbcField(DbcType.UINT32)
    public long minFactionId;

    @DbcField(DbcType.UINT32)
    public long minReputation;

    @DbcField(DbcType.UINT32)
    public long requiredAuraVision;

    @DbcField(DbcType.UINT32)
    public long requiredTotemCategoryId0;

    @DbcField(DbcType.UINT32)
    public long requiredTotemCategoryId1;

    @DbcField(DbcType.INT32)
    public int requiredAreasId;

    @DbcField(DbcType.UINT32)
    public long schoolMask;

    @DbcField(DbcType.UINT32)
    public long runeCostId;

    @DbcField(DbcType.UINT32)
    public long spellMissileId;

    @DbcField(DbcType.INT32)
    public int powerDisplayId;

    @DbcField(DbcType.FLOAT)
    public float effectBonusCoefficient0;

    @DbcField(DbcType.FLOAT)
    public float effectBonusCoefficient1;

    @DbcField(DbcType.FLOAT)
    public float effectBonusCoefficient2;

    @DbcField(DbcType.INT32)
    public int descriptionVariablesId;

    @DbcField(DbcType.UINT32)
    public long difficulty;

    @Override
    public String toString() {
        return "SpellDbc{" +
            "id=" + id +
            ", category=" + category +
            ", dispelType=" + dispelType +
            ", mechanic=" + mechanic +
            ", attributes=" + attributes +
            ", attributesEx=" + attributesEx +
            ", attributesExB=" + attributesExB +
            ", attributesExC=" + attributesExC +
            ", attributesExD=" + attributesExD +
            ", attributesExE=" + attributesExE +
            ", attributesExF=" + attributesExF +
            ", attributesExG=" + attributesExG +
            ", shapeshiftMask0=" + shapeshiftMask0 +
            ", shapeshiftMask1=" + shapeshiftMask1 +
            ", shapeshiftExclude0=" + shapeshiftExclude0 +
            ", shapeshiftExclude1=" + shapeshiftExclude1 +
            ", targets=" + targets +
            ", targetCreatureType=" + targetCreatureType +
            ", requiresSpellFocus=" + requiresSpellFocus +
            ", facingCasterFlags=" + facingCasterFlags +
            ", casterAuraState=" + casterAuraState +
            ", targetAuraState=" + targetAuraState +
            ", excludeCasterAuraState=" + excludeCasterAuraState +
            ", excludeTargetAuraState=" + excludeTargetAuraState +
            ", casterAuraSpell=" + casterAuraSpell +
            ", targetAuraSpell=" + targetAuraSpell +
            ", excludeCasterAuraSpell=" + excludeCasterAuraSpell +
            ", excludeTargetAuraSpell=" + excludeTargetAuraSpell +
            ", castingTimeIndex=" + castingTimeIndex +
            ", recoveryTime=" + recoveryTime +
            ", categoryRecoveryTime=" + categoryRecoveryTime +
            ", interruptFlags=" + interruptFlags +
            ", auraInterruptFlags=" + auraInterruptFlags +
            ", channelInterruptFlags=" + channelInterruptFlags +
            ", procTypeMask=" + procTypeMask +
            ", procChance=" + procChance +
            ", procCharges=" + procCharges +
            ", maxLevel=" + maxLevel +
            ", baseLevel=" + baseLevel +
            ", spellLevel=" + spellLevel +
            ", durationIndex=" + durationIndex +
            ", powerType=" + powerType +
            ", manaCost=" + manaCost +
            ", manaCostPerLevel=" + manaCostPerLevel +
            ", manaPerSecond=" + manaPerSecond +
            ", manaPerSecondPerLevel=" + manaPerSecondPerLevel +
            ", rangeIndex=" + rangeIndex +
            ", speed=" + speed +
            ", modalNextSpell=" + modalNextSpell +
            ", cumulativeAura=" + cumulativeAura +
            ", totem0=" + totem0 +
            ", totem1=" + totem1 +
            ", reagent0=" + reagent0 +
            ", reagent1=" + reagent1 +
            ", reagent2=" + reagent2 +
            ", reagent3=" + reagent3 +
            ", reagent4=" + reagent4 +
            ", reagent5=" + reagent5 +
            ", reagent6=" + reagent6 +
            ", reagent7=" + reagent7 +
            ", reagentCount0=" + reagentCount0 +
            ", reagentCount1=" + reagentCount1 +
            ", reagentCount2=" + reagentCount2 +
            ", reagentCount3=" + reagentCount3 +
            ", reagentCount4=" + reagentCount4 +
            ", reagentCount5=" + reagentCount5 +
            ", reagentCount6=" + reagentCount6 +
            ", reagentCount7=" + reagentCount7 +
            ", equippedItemClass=" + equippedItemClass +
            ", equippedItemSubclass=" + equippedItemSubclass +
            ", equippedItemInvTypes=" + equippedItemInvTypes +
            ", effect0=" + effect0 +
            ", effect1=" + effect1 +
            ", effect2=" + effect2 +
            ", effectDieSides0=" + effectDieSides0 +
            ", effectDieSides1=" + effectDieSides1 +
            ", effectDieSides2=" + effectDieSides2 +
            ", effectRealPointsPerLevel0=" + effectRealPointsPerLevel0 +
            ", effectRealPointsPerLevel1=" + effectRealPointsPerLevel1 +
            ", effectRealPointsPerLevel2=" + effectRealPointsPerLevel2 +
            ", effectBasePoints0=" + effectBasePoints0 +
            ", effectBasePoints1=" + effectBasePoints1 +
            ", effectBasePoints2=" + effectBasePoints2 +
            ", effectMechanic0=" + effectMechanic0 +
            ", effectMechanic1=" + effectMechanic1 +
            ", effectMechanic2=" + effectMechanic2 +
            ", effectImplicitTargetA0=" + effectImplicitTargetA0 +
            ", effectImplicitTargetA1=" + effectImplicitTargetA1 +
            ", effectImplicitTargetA2=" + effectImplicitTargetA2 +
            ", effectImplicitTargetB0=" + effectImplicitTargetB0 +
            ", effectImplicitTargetB1=" + effectImplicitTargetB1 +
            ", effectImplicitTargetB2=" + effectImplicitTargetB2 +
            ", effectRadiusIndex0=" + effectRadiusIndex0 +
            ", effectRadiusIndex1=" + effectRadiusIndex1 +
            ", effectRadiusIndex2=" + effectRadiusIndex2 +
            ", effectAura0=" + effectAura0 +
            ", effectAura1=" + effectAura1 +
            ", effectAura2=" + effectAura2 +
            ", effectAuraPeriod0=" + effectAuraPeriod0 +
            ", effectAuraPeriod1=" + effectAuraPeriod1 +
            ", effectAuraPeriod2=" + effectAuraPeriod2 +
            ", effectAmplitude0=" + effectAmplitude0 +
            ", effectAmplitude1=" + effectAmplitude1 +
            ", effectAmplitude2=" + effectAmplitude2 +
            ", effectChainTargets0=" + effectChainTargets0 +
            ", effectChainTargets1=" + effectChainTargets1 +
            ", effectChainTargets2=" + effectChainTargets2 +
            ", effectItemType0=" + effectItemType0 +
            ", effectItemType1=" + effectItemType1 +
            ", effectItemType2=" + effectItemType2 +
            ", effectMiscValue0=" + effectMiscValue0 +
            ", effectMiscValue1=" + effectMiscValue1 +
            ", effectMiscValue2=" + effectMiscValue2 +
            ", effectMiscValueB0=" + effectMiscValueB0 +
            ", effectMiscValueB1=" + effectMiscValueB1 +
            ", effectMiscValueB2=" + effectMiscValueB2 +
            ", effectTriggerSpell0=" + effectTriggerSpell0 +
            ", effectTriggerSpell1=" + effectTriggerSpell1 +
            ", effectTriggerSpell2=" + effectTriggerSpell2 +
            ", effectPointsPerCombo0=" + effectPointsPerCombo0 +
            ", effectPointsPerCombo1=" + effectPointsPerCombo1 +
            ", effectPointsPerCombo2=" + effectPointsPerCombo2 +
            ", effectSpellClassMaskA0=" + effectSpellClassMaskA0 +
            ", effectSpellClassMaskA1=" + effectSpellClassMaskA1 +
            ", effectSpellClassMaskA2=" + effectSpellClassMaskA2 +
            ", effectSpellClassMaskB0=" + effectSpellClassMaskB0 +
            ", effectSpellClassMaskB1=" + effectSpellClassMaskB1 +
            ", effectSpellClassMaskB2=" + effectSpellClassMaskB2 +
            ", effectSpellClassMaskC0=" + effectSpellClassMaskC0 +
            ", effectSpellClassMaskC1=" + effectSpellClassMaskC1 +
            ", effectSpellClassMaskC2=" + effectSpellClassMaskC2 +
            ", spellVisualId0=" + spellVisualId0 +
            ", spellVisualId1=" + spellVisualId1 +
            ", spellIconId=" + spellIconId +
            ", activeIconId=" + activeIconId +
            ", spellPriority=" + spellPriority +
            ", name0='" + name0 + '\'' +
            ", name1='" + name1 + '\'' +
            ", name2='" + name2 + '\'' +
            ", name3='" + name3 + '\'' +
            ", name4='" + name4 + '\'' +
            ", name5='" + name5 + '\'' +
            ", name6='" + name6 + '\'' +
            ", name7='" + name7 + '\'' +
            ", name8='" + name8 + '\'' +
            ", name9='" + name9 + '\'' +
            ", name10='" + name10 + '\'' +
            ", name11='" + name11 + '\'' +
            ", name12='" + name12 + '\'' +
            ", name13='" + name13 + '\'' +
            ", name14='" + name14 + '\'' +
            ", name15='" + name15 + '\'' +
            ", namelangmask=" + namelangmask +
            ", nameSubtext0='" + nameSubtext0 + '\'' +
            ", nameSubtext1='" + nameSubtext1 + '\'' +
            ", nameSubtext2='" + nameSubtext2 + '\'' +
            ", nameSubtext3='" + nameSubtext3 + '\'' +
            ", nameSubtext4='" + nameSubtext4 + '\'' +
            ", nameSubtext5='" + nameSubtext5 + '\'' +
            ", nameSubtext6='" + nameSubtext6 + '\'' +
            ", nameSubtext7='" + nameSubtext7 + '\'' +
            ", nameSubtext8='" + nameSubtext8 + '\'' +
            ", nameSubtext9='" + nameSubtext9 + '\'' +
            ", nameSubtext10='" + nameSubtext10 + '\'' +
            ", nameSubtext11='" + nameSubtext11 + '\'' +
            ", nameSubtext12='" + nameSubtext12 + '\'' +
            ", nameSubtext13='" + nameSubtext13 + '\'' +
            ", nameSubtext14='" + nameSubtext14 + '\'' +
            ", nameSubtext15='" + nameSubtext15 + '\'' +
            ", nameSubtextlangmask=" + nameSubtextlangmask +
            ", description0='" + description0 + '\'' +
            ", description1='" + description1 + '\'' +
            ", description2='" + description2 + '\'' +
            ", description3='" + description3 + '\'' +
            ", description4='" + description4 + '\'' +
            ", description5='" + description5 + '\'' +
            ", description6='" + description6 + '\'' +
            ", description7='" + description7 + '\'' +
            ", description8='" + description8 + '\'' +
            ", description9='" + description9 + '\'' +
            ", description10='" + description10 + '\'' +
            ", description11='" + description11 + '\'' +
            ", description12='" + description12 + '\'' +
            ", description13='" + description13 + '\'' +
            ", description14='" + description14 + '\'' +
            ", description15='" + description15 + '\'' +
            ", descriptionlangmask=" + descriptionlangmask +
            ", auraDescription0='" + auraDescription0 + '\'' +
            ", auraDescription1='" + auraDescription1 + '\'' +
            ", auraDescription2='" + auraDescription2 + '\'' +
            ", auraDescription3='" + auraDescription3 + '\'' +
            ", auraDescription4='" + auraDescription4 + '\'' +
            ", auraDescription5='" + auraDescription5 + '\'' +
            ", auraDescription6='" + auraDescription6 + '\'' +
            ", auraDescription7='" + auraDescription7 + '\'' +
            ", auraDescription8='" + auraDescription8 + '\'' +
            ", auraDescription9='" + auraDescription9 + '\'' +
            ", auraDescription10='" + auraDescription10 + '\'' +
            ", auraDescription11='" + auraDescription11 + '\'' +
            ", auraDescription12='" + auraDescription12 + '\'' +
            ", auraDescription13='" + auraDescription13 + '\'' +
            ", auraDescription14='" + auraDescription14 + '\'' +
            ", auraDescription15='" + auraDescription15 + '\'' +
            ", auraDescriptionlangmask=" + auraDescriptionlangmask +
            ", manaCostPct=" + manaCostPct +
            ", startRecoveryCategory=" + startRecoveryCategory +
            ", startRecoveryTime=" + startRecoveryTime +
            ", maxTargetLevel=" + maxTargetLevel +
            ", spellClassSet=" + spellClassSet +
            ", spellClassMask0=" + spellClassMask0 +
            ", spellClassMask1=" + spellClassMask1 +
            ", spellClassMask2=" + spellClassMask2 +
            ", maxTargets=" + maxTargets +
            ", defenseType=" + defenseType +
            ", preventionType=" + preventionType +
            ", stanceBarOrder=" + stanceBarOrder +
            ", effectChainAmplitude0=" + effectChainAmplitude0 +
            ", effectChainAmplitude1=" + effectChainAmplitude1 +
            ", effectChainAmplitude2=" + effectChainAmplitude2 +
            ", minFactionId=" + minFactionId +
            ", minReputation=" + minReputation +
            ", requiredAuraVision=" + requiredAuraVision +
            ", requiredTotemCategoryId0=" + requiredTotemCategoryId0 +
            ", requiredTotemCategoryId1=" + requiredTotemCategoryId1 +
            ", requiredAreasId=" + requiredAreasId +
            ", schoolMask=" + schoolMask +
            ", runeCostId=" + runeCostId +
            ", spellMissileId=" + spellMissileId +
            ", powerDisplayId=" + powerDisplayId +
            ", effectBonusCoefficient0=" + effectBonusCoefficient0 +
            ", effectBonusCoefficient1=" + effectBonusCoefficient1 +
            ", effectBonusCoefficient2=" + effectBonusCoefficient2 +
            ", descriptionVariablesId=" + descriptionVariablesId +
            ", difficulty=" + difficulty +
            '}';
    }

}
