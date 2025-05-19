package net.kwas.acore.antlr.resolver.reference;

import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.NumberResolver;

public record DamageBoundResolver(int index, Long spellId, boolean isUpperBound) implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        var mySpellId = spellId != null ? spellId : ctx.getSpellId();

        var actualIndex = index - 1;
        var spellInfo = ctx.getSpellInfos().get(mySpellId);
        var baseValue = spellInfo.baseValues().get(actualIndex);

        var effectPerLevel = spellInfo.baseValuePerLevels().get(actualIndex);
        var characterLevel = ctx.getCharacterInfo().characterLevel();
        var perLevelBonus = effectPerLevel * characterLevel;

        var dieSides = spellInfo.dieSides().get(actualIndex);

        var roll = isUpperBound ? dieSides : 1;

        // TODO: I feel like we need to incorporate spell power and coefficient or something.....
        return baseValue + perLevelBonus + roll;
    }

}
