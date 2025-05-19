package net.kwas.acore.antlr.resolver.reference;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

public record AuraPeriodResolver(Long spellId, int index) implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        var mySpellId = spellId != null ? spellId : ctx.getSpellId();
        return ctx.getSpellInfos().get(mySpellId).auraPeriods().get(index);
    }

}
