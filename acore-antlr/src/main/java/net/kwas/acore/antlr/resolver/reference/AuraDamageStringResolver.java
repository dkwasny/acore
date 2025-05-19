package net.kwas.acore.antlr.resolver.reference;

import net.kwas.acore.antlr.resolver.StringResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;
import net.kwas.acore.antlr.resolver.util.DamageUtils;

public record AuraDamageStringResolver(int index, Long spellId) implements StringResolver {

    @Override
    public String resolveString(SpellContext ctx) {
        var lowerBoundResolver = new DamageBoundResolver(index, spellId, false);
        var baseLowerBound = lowerBoundResolver.resolveNumber(ctx);

        var upperBoundResolver = new DamageBoundResolver(index, spellId, true);
        var baseUpperBound = upperBoundResolver.resolveNumber(ctx);

        var auraPeriodResolver = new AuraPeriodResolver(spellId, index);
        var auraPeriod = auraPeriodResolver.resolveNumber(ctx);

        var durationResolver = new DurationResolver(spellId);
        var duration = durationResolver.resolveNumber(ctx);

        var lowerBound = baseLowerBound / (auraPeriod / 1000.0) * duration;
        var upperBound = baseUpperBound / (auraPeriod / 1000.0) * duration;

        return DamageUtils.renderSpellDamage(lowerBound, upperBound);
    }

}
