package net.kwas.acore.antlr.resolver.reference;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

public record AuraPeriodResolver(Long spellId, int index) implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        var mySpellId = spellId != null ? spellId : ctx.getSpellId();
        var msValue = ctx.getSpellInfos().get(mySpellId).auraPeriods().get(index);
        // TODO: We may need to do more intelligent logic for normalizing periods.
        // The one spell I looked at had `1 sec` in the description but the period was in ms (1000).
        // Blindly doing the division here for now.
        return msValue / 1000.0;
    }

}
