package net.kwas.acore.antlr.resolver.reference;

import net.kwas.acore.antlr.resolver.NumberResolver;
import net.kwas.acore.antlr.resolver.context.SpellContext;

public record AuraDamageBoundResolver(int index, Long spellId, boolean isUpperBound) implements NumberResolver {

    @Override
    public double resolveNumber(SpellContext ctx) {
        var baseDamageResolver = new DamageBoundResolver(index, spellId, isUpperBound);
        var baseDamage = baseDamageResolver.resolveNumber(ctx);

        var auraPeriodResolver = new AuraPeriodResolver(spellId, index);
        var auraPeriod = auraPeriodResolver.resolveNumber(ctx);

        var durationResolver = new DurationResolver(spellId);
        var duration = durationResolver.resolveNumber(ctx);

        return baseDamage / (auraPeriod / 1000.0) * duration;
    }

}
