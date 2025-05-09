package net.kwas.acore.antlr.resolver.reference;

import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.NumberResolver;

public record MultiplierResolver(int index, Long spellId) implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        var mySpellId = spellId != null ? spellId : ctx.getSpellId();

        var actualIndex = index - 1;
        var spellInfo = ctx.getSpellInfos().get(mySpellId);
        var baseValue = spellInfo.baseValues().get(actualIndex);
        var dieSides = spellInfo.dieSides().get(actualIndex);

        // TODO NEEDS TO BE <baseValue> + <1 for die count 1> at least
        // I feel like we need to incorporate spell power and coefficient or something.....
        // Who knows what to do if die count is > 1...
        return baseValue + dieSides;
    }

}
