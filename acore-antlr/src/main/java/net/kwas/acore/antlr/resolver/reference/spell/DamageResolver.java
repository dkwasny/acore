package net.kwas.acore.antlr.resolver.reference.spell;

import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.util.ResolverUtils;

public record DamageResolver(int index, Long spellId, boolean isMax) implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        var mySpellId = ResolverUtils.getSpellId(spellId, ctx);

        var actualIndex = index - 1;
        var spellInfo = ctx.getSpellInfos().get(mySpellId);
        var baseValue = spellInfo.baseValues().get(actualIndex);

        var effectPerLevel = spellInfo.baseValuePerLevels().get(actualIndex);
        var characterLevel = ctx.getCharacterInfo().characterLevel();
        var perLevelBonus = effectPerLevel * characterLevel;

        var dieSides = spellInfo.dieSides().get(actualIndex);

        var roll = isMax ? dieSides : 1;

        // TODO: I feel like we need to incorporate spell power and coefficient or something.....
        return baseValue + perLevelBonus + roll;
    }

}
