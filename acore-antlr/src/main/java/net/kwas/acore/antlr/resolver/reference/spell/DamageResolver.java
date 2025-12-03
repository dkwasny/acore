package net.kwas.acore.antlr.resolver.reference.spell;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.util.ResolverUtils;

public record DamageResolver(int index, Long spellId, boolean isMax) implements NumberResolver {

  @Override
  public double resolveNumber(SpellContext ctx) {
    var mySpellId = ResolverUtils.getSpellId(spellId, ctx);

    var spellInfo = ctx.getSpellInfo(mySpellId);
    var baseValue = spellInfo.baseValues().get(index);

    var characterLevel = ctx.getCharacterInfo().characterLevel();
    var baseLevel = spellInfo.baseLevel();
    var maxLevel = spellInfo.maxLevel();
    var levelMultiplier = Math.min(characterLevel, maxLevel) - baseLevel;

    var effectPerLevel = spellInfo.baseValuePerLevels().get(index);
    var perLevelBonus = effectPerLevel * levelMultiplier;

    var dieSides = spellInfo.dieSides().get(index);

    var roll = isMax ? dieSides : 1;

    // TODO: I feel like we need to incorporate spell power and coefficient or something.....
    return baseValue + perLevelBonus + roll;
  }

}
