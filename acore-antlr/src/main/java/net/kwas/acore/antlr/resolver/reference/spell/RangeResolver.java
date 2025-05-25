package net.kwas.acore.antlr.resolver.reference.spell;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.util.ResolverUtils;

public record RangeResolver(Long spellId, int index, boolean isMax) implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        var mySpellId = ResolverUtils.getSpellId(spellId, ctx);
        var spellInfo = ctx.getSpellInfos().get(mySpellId);
        return isMax
            ? spellInfo.maxRanges().get(index)
            : spellInfo.minRanges().get(index);
    }

}
